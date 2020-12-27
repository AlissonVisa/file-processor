create table if not exists salesman_database.salesman
(
    name text,
    import_archive text,
    cpf text,
    salary decimal,
    primary key ( import_archive, name )
    )
    with clustering order by (name asc)
    and caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
    and compaction = {'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy', 'max_threshold': '32', 'min_threshold': '4'}
    and compression = {'chunk_length_in_kb': '64', 'class': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    and dclocal_read_repair_chance = 0.1;