package com.mob.web;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;

public class RegexUtil{

	public static final ImmutableList<Character> wildcardChars = ImmutableList.of('*','?','+','{');

    public static final Map<Character, Character> escapeMappings = Maps.newHashMap();//{{{
    static{
        escapeMappings.put('A', null);
        escapeMappings.put('b', null);
        escapeMappings.put('B', null);
        escapeMappings.put('d', '0');
        escapeMappings.put('D', 'x');
        escapeMappings.put('s', ' ');
        escapeMappings.put('S', 'x');
        escapeMappings.put('w', 'x');
        escapeMappings.put('W', '!');
        escapeMappings.put('Z', null);
    }//}}}

    public static Iterator<Character> iterateChars(final String s){//{{{
        return new AbstractIterator<Character>(){
            int index = 0;
            public Character computeNext(){
                if( index > ( s.length() - 1 ) ){
                    return endOfData();
                }else{
                    return s.charAt( index++ );
                }
            }
        };
    }//}}}
                                          
    public static Iterator<RegexChar> getEscapedChars(final Iterator<Character> chars){//{{{
        return new AbstractIterator<RegexChar>(){
            protected RegexChar computeNext(){
                if( !chars.hasNext() ) return endOfData();
                Character nextChar = chars.next();
                if( !nextChar.equals( '\\' ) ){
                    return new RegexChar( nextChar, false );
                }else{
                    Character nextChar2nd = chars.next();
                    Character mapped = escapeMappings.get(nextChar2nd);
                    if( mapped == null ){
                        return computeNext();
                    }else{
                        return new RegexChar(mapped, true);
                    }
                }
            }
        };
    }//}}}

	public static void walkToEnd(Character ch, Iterator<RegexChar> chars){//{{{
		int nesting = ch.equals('(') ? 1 : 0;
		while( true ){
			RegexChar nextChar = chars.next();
			if( nextChar.representative ){
				continue;
			}else if( nextChar.character.equals('(') ){
				nesting++;
			}else if( nextChar.character.equals(')') ){
				if( nesting == 0 ){
					return;
				}else{
					nesting--;
				}
			}
		}
	}//}}}

 	public static Pair<Integer, Character> getQuantifier(Character ch, Iterator<RegexChar> chars){//{{{
        if( ch.equals('*') || ch.equals('?') || ch.equals('+') ){
            Character ch2 = null;
			if( chars.hasNext() ){
                RegexChar rch2 = chars.next();
                ch2 = rch2.character;
            }else{
                ch2 = null;
            }
            if( ch2 != null && ch2.equals('?') ){
                ch2 = null;
            }
            if ( ch.equals('+') ){
                return new Pair<Integer, Character>(1, ch2);
            }
            return new Pair<Integer, Character>(0, ch2);
        }
   
        String quant = "";
        RegexChar nextChar = chars.next();
        while ( !nextChar.character.equals('}') ){
            quant += nextChar.character;
            nextChar = chars.next();
        }
        String quantValue = ( quant.indexOf(",")>=0 ) ? quant.split(",")[0].trim() : quant.trim();
   
        if( chars.hasNext() ){
            RegexChar lastChar = chars.next();
            ch = lastChar.character;
            if( ch.equals('?') ) ch = null;
        }else{
            ch = null;
        }
        return new Pair<Integer, Character>( new Integer(quantValue), ch );
    }//}}}

    public static class Pair<T1,T2>{//{{{
        final T1 first;
        final T2 second;

        public Pair(T1 first, T2 second){
            this.first = first;
            this.second = second;
        }

        public String toString(){
            return (this.first != null ? this.first.toString() : "(null)") + ":"
                   + (this.second != null ? this.second.toString() : "(null)");
        }

        public T1 first(){ return this.first; }
        public T2 second(){ return this.second; }

    }//}}}

}
