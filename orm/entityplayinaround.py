#!/usr/bin/python
import sys
import re, pickle, MySQLdb, os
from hashlib import md5
from itertools import groupby
from mako.template import Template
import getopt

TABLES = {}
TYPEDICT = {#{{{
    'char':'String',
    'varchar':'String',
    'tinytext':'String',
    'text':'String',
    'mediumtext':'String',
    'longtext':'String',
    'binary':'byte[]',
    'varbinary':'byte[]',
    'tinyblob':'byte[]',
    'blob':'byte[]',
    'mediumblob':'byte[]',
    'longlob':'byte[]',
    'tinyint':'Integer',
    'smallint':'Integer',
    'mediumint':'Integer',
    'int':'Integer',
    'bigint':'Long',
    'float':'Float',
    'decimal':'BigDecimal',
    'double':'Double',
    'timestamp':'org.joda.DateTime',
    'datetime':'org.joda.DateTime',
    'date':'org.joda.LocalDate',
    'time':'java.util.Date'
    }#}}}

def ex_type(type_len):#{{{
    try:
        return re.match("^(.*)\\(.*$", type_len).group(1)
    except:
        return type_len#}}}

def ex_len(type_len):#{{{
    try:
        return re.match("^.*\\((\d+)(,\d+)?\\).*?$", type_len).group(1)
    except:
        return None#}}}

def ex_fkey(col_def):#{{{
    try:
        return re.match("(\w+)_id$", col_def).group(1)
    except:
        try:
            return re.match("^(\w+)ID$", col_def).group(1)
        except:
            return None#}}}

class TableInfo(object):#{{{
    def __init__(self, tablename, cursor):
        self.tablename = tablename
        self.classname = tr_table(tablename)
        self.cols = []
        self.refs = []
        self.primary_key = None
        for row in q(cursor, "show columns from %s" % (self.tablename)):
            name, type, length, default, extra = row[0], ex_type(row[1]), ex_len(row[1]), row[4], row[5]
            self.cols.append( ColumnInfo(name, type, length, default, extra) )
        self.indexes = []
        self.foreign_keys = []
        #for col in [col.name for col in self.cols if ex_fkey(col.name)]:
        for col in self.cols:
            if ex_fkey(col.name):
                self.foreign_keys.append(ForeignKey(col.name))
            if col.extra == 'auto_increment':
                self.primary_key = col

    def __repr__(self):
        output = "table: %s (%s columns)" % (self.tablename, len(self.cols))
        output += '\n\t' + '\n\t'.join(str(column) for column in self.cols) + "\n"
        return output#}}}

class ColumnInfo(object):#{{{
    def __init__(self, name, type, length, default, extra):
        self.name = name
        self.type = type
        self.length = length
        self.default = default
        self.extra = extra
        self.java_name = tr_name(name)
        self.java_caps_name = tr_table(name)
        self.java_type = TYPEDICT[type]


    def __str__(self):
        return "%s: %s (len: %s, default: %s, extra: %s, javatype: %s)" % (self.name, self.type, self.length, self.default, self.extra, self.java_type)

    def __repr__(self):
        return "%s: %s (len: %s, default: %s, extra: %s)" % (self.name, self.type, self.length, self.default, self.extra)
#}}}

class ForeignKey(object):
    def __init__(self, name, direct=True):
        self.name = name;

    def __repr__(self):
        return self.name
#         self.default = default or ''
#         if self.type == "String":
#             self.default = '"%s"' % (self.default)
#         elif self.sql_type == "timestamp":
#             self.default = "now"
#         else:
#             self.default = MAGIC_TYPE_DEFAULTS.get(self.type, {}).get(self.default, self.default)
#             self.default = self.default or "null"
#         self.default_init = TYPE_INIT.get(self.type, lambda x: x)(self.default)
#         self.extra = extra
                
        # a little bit of type dependant twiddling
#         if self.sql_type == FLOATTYPE and self.length:
#             self.length += 1
#         elif self.sql_type == TEXT:
#             self.length = 65000
#         elif self.sql_type == MEDIUMTEXT:
#             self.length = 16000000
#         elif self.sql_type == DATE:
#             self.length = 10
#         elif self.sql_type == TIME:
#             self.length = 8
#         elif self.sql_type == DATETIME and self.default == 'null':
#             self.length = 19    

#     def arg_print(self):
#         if self.sql_type == 'timestamp' or self.sql_type == 'datetime':
#             return "( ( this.%s != null ) ? new java.sql.Timestamp( this.%s.getTime() ) : null )" % \
#                 (self.camel_name, self.camel_name)
#         elif self.sql_type == 'date':
#             return "( ( this.%s != null ) ? new java.sql.Date( this.%s.getTime() ) : null )" % \
#                 (self.camel_name, self.camel_name)
#         elif self.sql_type == 'time':
#             return "( ( this.%s != null ) ? new java.sql.Time( this.%s.getTime() ) : null )" % \
#                 (self.camel_name, self.camel_name)
#         else:
#             return "this.%s" % (self.camel_name)

#from mako.template import Template

# class TableInfo(object):#{{{
#     """
#     All information for a particular table. Core table info and
#     reference to column and index objects. All table objects are
#     references in TABLES dict.
#     """
#     def __init__(self, table, cursor):#{{{
#         self.javaClass = tr_table(table)
#         self.javaTable = tr_table(table)
#         self.camelTable = tr_name(table)
#         self.table = table
#         self.auto_increment_col = "%s_id" % self.table
#         self.columns = []
#         self.refs = []
#         self.listRefs = []
#         self.indexes = []
#         self.primary_key = None
#         self.primary_key_col = None
#         self.primary_key_attr = None
#         self.primary_key_caps = None
#         #self.pool = POOL
#         # maintain map of table names to tables
#         TABLES[self.table] = self
# 
#         # populate from db.
#         self.pushCols(cursor)
#         self.column_dict = dict([(col.name, col) for col in self.columns])
#         # keep mapping of col names to cols
#         self.col_map = dict([(col.name, col) for col in self.columns])
#         # grab indexes
#         self.pushIndexes()#}}}
#         
#     def pushCols(self, cursor):#{{{
#         for row in q(cursor, "show columns from %s" % (self.table)):
#             name, type, length, default, extra = row[0], ex_type(row[1]), ex_len(row[1]), row[4], row[5]
#             self.pushCol(ColumnInfo(name, type, length, default, extra))#}}}

    def pushForeignKeys(self):#{{{
        for c in [c for c in self.columns if ex_fkey(c.name)]:
            if c.name != "board_id" and c.name != self.primary_key_col:
                try:
                    self.refs.append(ForeignKey(c.name))
                except:
                    pass # ok, it looks like a ref but doesn't ref another table#}}}

    def pushRemoteRefs(self):#{{{
        # punt on mvnForum for now
        if PREFIX: return
        for table in TABLES.values():
            table_name = table.table
            if table_name.startswith(self.table) and table_name != self.table:
                # if chapter we want chapter_member, not chapter_member_history
                us, rest = table_name.split(self.table)
                if len(rest[1:].split('_')) > 1:
                    if DEBUG: print "skipping remote ref, too far %s %s" % (us, rest)
                    continue 

                # if the table has a unique index on this tables primary
                # key. push it as a remote ref [ if we don't have a direct
                # reference to it already.
                for index in table.uniqueIndexes(True):
                    if len(index.columns) == 1 and index.columns[0].name == self.primary_key_col:
                        if DEBUG: print "pushing non-explict one-to-one ref " + table.primary_key_col
                        self.refs.append(ForeignKey(table.primary_key_col, direct=False))
                        continue
                    # if its not unique, but the table have an index that *begins* with this
                    # tables primary key then its a list type ref -- push it
                    elif len(index.columns) > 1 and index.columns[0].name == self.primary_key_col:
                        if DEBUG: print "pushing one-to-many ref " + table.primary_key_col
                        self.listRefs.append(table)
                        continue
                # todo, skip if ref table doesn't have a 'table_id' ref [ chapter_notify ]
                # todo, singular ref if unique index on 'table_id' in foreign table
                # -- just push onto direct refs?#}}}

    def pushIndexes(self):#{{{
        raw_indexes = [(r[0], list(r[1])) for r in groupby(q("show indexes from %s" % (self.table)), lambda x: x[2])]
        for index in raw_indexes:
            # data is ('idx_name', [[table, non_unique, idx_name, sequence, column, collation, \
            #    cardinality, sub_part, packed, null, type, comment], []] )
            name = index[0]
            unique = 1 ^ index[1][0][1]  
            cols = []
            for index_part in index[1]:
                cols.append(index_part[4])
            info = IndexInfo(name, unique, cols, self)
            self.indexes.append(info)
            # identify our primary key
            if (len(info.columns) == 1 and self.column_dict.get(info.columns[0].name).extra == "auto_increment"):
                info.name = "PRIMARY"
                self.primary_key = info
                self.primary_key_col = info.columns[0].name
                self.primary_key_attr = info.columns[0].camel_name
                self.primary_key_caps = info.columns[0].caps_name
            else:
                info.name = "PROCESSED"#}}}
                
    def uniqueIndexes(self, with_primary=False):#{{{
        return [index for index in self.indexes if index.unique and (index.name != "PRIMARY" or with_primary)]#}}}

    def pushCol(self, column):#{{{
        self.columns.append(column)#}}}

    def hasCol(self, col):#{{{
        return self.column_dict.has_key(col)#}}}#}}}

def q(cursor, query):
    cursor.execute(query)
    return cursor.fetchall()
def strip_(str):
    return re.sub('_', '', str)
def tr_table(str):
    return strip_(re.sub(r'(\b\w)|(_\w)', lambda x: x.group(0).upper(), str))
def tr_name(str):
    camel = strip_(re.sub(r'^.+(\b\w)|(_\w)', lambda x: x.group(0).upper(), str))
    return camel[0].lower() + camel[1:]

def main(argv):
    opts, args = getopt.getopt( argv, "d:p:t:", ["dir=","pkg=","templatedir="] )
    outputdir, package, templatedir = None, None, None
    for opt, arg in opts:
        if opt in ("-d", "--dir"):
            outputdir = arg
        elif opt in ("-p", "--pkg"):
            package = arg
        elif opt in ("-t", "--tempdir"):
            templatedir = arg
    if not outputdir or not package or not templatedir: usage()
    _DB = MySQLdb.connect("localhost", user="mike", passwd="", db="mike_testing")
    cursor = _DB.cursor()
    outputDirectory = "%s/src/orm/models/" % ( os.getcwd() )
    tables = []
    for table in [table[0] for table in q(cursor, "show tables") if not table[0].startswith('ne_')]:
        tobj = TableInfo(table, cursor)
        tables.append(tobj)
    template = Template(filename='%s/modeltemplate.mako' % (templatedir))

    for table in tables:
        outfile = file( outputdir + "/" + table.classname + '.java', 'w' )
        outfile.write( template.render(table=table, package=package) )
        outfile.close()
#     print tables
    print tables[0].foreign_keys
    print tables[0].primary_key

def usage():
    print "entity.py [-d outputdirectory] [-p package] [-t templatedir]"
    sys.exit(2)

#         if not FORCE and up_to_date(table):
#             if DEBUG: print "skipped %s -- up to date" % (table.table)
#             continue
#         if DEBUG: print "generating metadata for %s" % (table)

if __name__ == '__main__':
    main(sys.argv[1:])

