package com.mob.web;
import java.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import com.mob.web.RegexUtil.Pair;

public class ReverseMatch{

	private ArrayList<MatchingPart> parts = new ArrayList<MatchingPart>();

	public ReverseMatch(String regex) throws Exception{
		final Stack nonCapturingGroups = new Stack();
		Iterator<RegexChar> regexchars = RegexUtil.getEscapedChars( RegexUtil.iterateChars( regex ) );
		RegexChar regchar = regexchars.next();
		boolean consumeNext = true;
		Character ch = regchar.character;
		boolean escaped = regchar.representative;
		while( true ){
			if( ch.equals('.') ){
				appendLiteral('.');
			}else if( ch.equals('|') ){
				throw new Exception();
			}else if( ch.equals('^') ){
				//pass
			}else if( ch.equals('$') ){
				break;
			}else if( ch.equals(')') ){
				// end of a non-capturing group
				//start = nonCapturingGroups.pop();
			}else if( ch.equals('[') ){
				RegexChar rangeChar = regexchars.next();
				appendLiteral( rangeChar.character );
				rangeChar = regexchars.next();
				while( rangeChar.representative || !rangeChar.character.equals( ']' ) ){
					rangeChar = regexchars.next();
				}
			}else if( ch.equals('(') ){
				RegexChar groupTypeChar = regexchars.next(); 
				if( !groupTypeChar.character.equals('?') || groupTypeChar.representative ){
					//add a group, and then advance to the end of the group
					appendGroup( new GroupPart() );
					RegexUtil.walkToEnd( groupTypeChar.character, regexchars );
				}else{
					final RegexChar groupNextChar = regexchars.next();
					if( "iLmsu#".indexOf( groupNextChar.character ) >= 0 ){
						RegexUtil.walkToEnd( groupNextChar.character, regexchars );
					}else if(groupNextChar.character.equals(':') ){
						//pass
						//add a noncapturing group
					}else if( !groupNextChar.character.equals('P') ){
						throw new Exception("1can't reverse this shit");
					}else{
						RegexChar groupAnotherChar = regexchars.next();
						if( !groupAnotherChar.character.equals('<') ){
							throw new Exception("2can't reverse it");
						}else{
							String name = "";
							RegexChar nameChar = regexchars.next();
							while( nameChar.character != '>' ){
								name += nameChar.character;
								nameChar = regexchars.next();
							}
							appendGroup( new GroupPart(name) ); 
							RegexUtil.walkToEnd(nameChar.character, regexchars);
						}
					}
				}
			}else if( Iterables.any( RegexUtil.wildcardChars, Predicates.equalTo( ch ) ) ){
 				Pair<Integer, Character> quantified = RegexUtil.getQuantifier( ch, regexchars);
 				if( quantified.second() != null ){
 					consumeNext = false;
					ch = quantified.second();
 				}
  				if( quantified.first() == 0 ){
					optionalizeLast();
  				}else{
					extendQuantified(quantified.second());
				}
			}else{ //literal
				appendLiteral( regchar.character );
			}

			if( consumeNext ){
				if( regexchars.hasNext() ){
					regchar = regexchars.next();
					ch = regchar.character;
					escaped = regchar.representative;
				}else{
					break;
				}
			}else{
				consumeNext = true;
			}
		}
	}

 	public void appendLiteral(Character c){
 		String literalString;
 		if( parts.isEmpty() ){
			LiteralPart mp = new LiteralPart(c);
			parts.add( mp );
			return;
 		}else{
			MatchingPart p = parts.get( parts.size() - 1 );
			if( p instanceof LiteralPart ){
				((LiteralPart)p).append( c );
			}else{
				LiteralPart mp = new LiteralPart(c);
				parts.add( mp );
			}
		}
 	}

	public void appendGroup(GroupPart g){
		this.parts.add( g );
	}

	public String toString(){
		StringBuilder sb = new StringBuilder("");
		for( MatchingPart mp : parts ){
			sb.append( mp.toString() );
		}
		return sb.toString();
	}

	public void optionalizeLast(){
 		if( parts.isEmpty() ){
 			return;
		}else{
			MatchingPart mp = parts.get(parts.size() - 1);
			if( mp instanceof OptionalPart ){
				((OptionalPart)mp).setOptional(true);
			}
		}
	}

	public String reverse(Iterator<String> groups){
		StringBuilder reverseGen = new StringBuilder("");
		for( MatchingPart mp : this.parts ){
			if( mp instanceof LiteralPart ){
				reverseGen.append( ((LiteralPart)mp).getString() );
			}else if(mp instanceof GroupPart){
				if( groups.hasNext() ){
					String nextArg = groups.next() + "";
					reverseGen.append( nextArg );
				}else{
					if( ((GroupPart)mp).getOptional() ){
						//it's ok
					}else{
						throw new IllegalArgumentException("Bad args");
					}
				}
			}
		}
		return reverseGen.toString();
	}

	public void extendQuantified(int count){
 		if( parts.isEmpty() ){
 			return;
		}else{
			MatchingPart mp = parts.get(parts.size() - 1);
			if( mp instanceof LiteralPart ){
				LiteralPart lp = (LiteralPart)mp;
				String root = lp.getString();
				Character last = root.charAt(root.length()-1);
				StringBuilder repeated = new StringBuilder("");
				for( int i=0; i<count; i++){
					repeated.append( last );
				}
				lp.reset( root.substring(0, root.length()-1) + repeated.toString() );
				((OptionalPart)mp).setOptional(true);
			}
		}
	} 

	public String reverse(String... groups){
		return reverse( Arrays.asList( groups ).iterator() );
	}

	

}
