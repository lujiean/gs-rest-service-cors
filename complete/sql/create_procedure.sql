delimiter $$
drop procedure if exists ljjtest_sp1$$
create procedure ljjtest_sp1(
    IN fi_id integer,
    OUT fo_email varchar(255),
    OUT fo_name varchar(255)
    )
begin
    -- declare f_email varchar(255);
    -- declare f_name varchar(255);

    select email, name
      into fo_email, fo_name
      from user
     where id = fi_id;
    -- set @f_email = 'testemail';
    -- set @f_name = 'testname';
    select fo_email, fo_name;
end$$
delimiter ;
