create user pozoriste identified by pozoriste
	default tablespace USERS temporary tablespace TEMP;


	grant connect, resource to pozoriste;

	grant create table to pozoriste;

	grant create view to pozoriste;

	grant create procedure to pozoriste;

	grant create synonym to pozoriste;

	grant create sequence to pozoriste;

	grant select on dba_rollback_segs to pozoriste;

	grant select on dba_segments to pozoriste;

	grant unlimited tablespace to pozoriste;