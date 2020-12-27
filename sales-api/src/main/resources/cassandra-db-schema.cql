create table if not exists sales_database.sale
(
    archive_name text,
    salesman text,
    totalvalue decimal,
    id bigint,
    primary key ((archive_name, id), totalvalue)
    )
with clustering order by (totalvalue desc)
	and caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
	and compaction = {'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy', 'max_threshold': '32', 'min_threshold': '4'}
	and compression = {'chunk_length_in_kb': '64', 'class': 'org.apache.cassandra.io.compress.LZ4Compressor'}
	and dclocal_read_repair_chance = 0.1;

create index if not exists sale_archive_name_index
	on sales_database.sale (archive_name);