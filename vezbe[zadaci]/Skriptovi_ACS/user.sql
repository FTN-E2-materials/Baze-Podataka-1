create user ra1862017 identified by ftn
	default tablespace USERS temporary tablespace TEMP;


	grant connect, resource to ra1862017;

	grant create table to ra1862017;

	grant create view to ra1862017;

	grant create procedure to ra1862017;

	grant create synonym to ra1862017;

	grant create sequence to ra1862017;

	grant select on dba_rollback_segs to ra1862017;

	grant select on dba_segments to ra1862017;

	grant unlimited tablespace to ra1862017;