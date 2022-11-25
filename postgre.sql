create table IF NOT EXISTS dev.customer_master(customer_id SERIAL,
customer_code varchar(255), 
email varchar(100), 
name varchar(100), 
mobile varchar(100), 
address1 varchar(255), 
address2 varchar(255), 
country varchar(50), 	
state varchar(50), 
city varchar(100), 
postal_code varchar(100), 
contact_name varchar(100), 
designation varchar(255), 
phone varchar(100), 
web_address varchar(255),
customer_location varchar(255),
customer_logo varchar(255),
CreatedBy int,
CreatedDate timestamp,
ModifiedBy int,
ModifiedDate timestamp,
Status bit,
PRIMARY KEY(customer_id)
); 


create tableIF NOT EXISTS dev.project_master(project_id SERIAL,
project_code varchar(255), 
project_name varchar(255), 
project_location varchar(100), 
city varchar(100), 
postal_code varchar(50), 
state varchar(50), 
country varchar(50), 
destination_location varchar(50), 
buyername varchar(100), 
designation varchar(100), 
telephone varchar(100), 
mobile varchar(100), 
email varchar(100), 
web_address varchar(100),
CreatedBy int,
CreatedDate timestamp,
ModifiedBy int,
ModifiedDate timestamp,
Status bit,
PRIMARY KEY(project_id)
);

create table IF NOT EXISTS dev.packaging_type(id SERIAL,
type varchar(255), 
description varchar(100), 
PRIMARY KEY(id)
);



CREATE TABLE IF NOT EXISTS dev.country (
  id SERIAL,
  iso char(2) NOT NULL,
  name varchar(80) NOT NULL,
  nicename varchar(80) NOT NULL,
  iso3 char(3) DEFAULT NULL,
  numcode smallint(6) DEFAULT NULL,
  phonecode int(5) NOT NULL,
  PRIMARY KEY (id)
) 


CREATE TABLE IF NOT EXISTS dev.item_master (
  item_id SERIAL,
  customer_code int(11) DEFAULT NULL,
  project_code int(11) DEFAULT NULL,
  org_country_id int(11) DEFAULT NULL,
  packing_type int(11) DEFAULT NULL,
  lot_size varchar(100) DEFAULT NULL,
  customer_login int(11) DEFAULT NULL,
  lot_ref_no varchar(100) DEFAULT NULL,
  containers varchar(100) DEFAULT NULL,
  CreatedBy int(11) DEFAULT NULL,
  CreatedDate datetime DEFAULT NULL,
  ModifiedBy int(11) DEFAULT NULL,
  ModifiedDate datetime DEFAULT NULL,
  Status bit(1) DEFAULT NULL,
  PRIMARY KEY (item_id)
)



CREATE TABLE IF NOT EXISTS dev.tblbom (
  BOM_ID SERIAL,
  item_master_id int DEFAULT NULL,
  PART_NO varchar(100) DEFAULT NULL,
  PART_DESCRIPTION varchar(100) DEFAULT NULL,
  Version varchar(100) DEFAULT NULL,
  ST_LOCATION varchar(100) DEFAULT NULL,
  VALIDITY varchar(100) DEFAULT NULL,
  CAT_Description varchar(100) DEFAULT NULL,
  QTY_REQUIRED varchar(100) DEFAULT NULL,
  Qty_Lot varchar(100) DEFAULT NULL,
  Primary_NO varchar(100) DEFAULT NULL,
  Secondary_NO varchar(100) DEFAULT NULL,
  Pack_Code varchar(1000) DEFAULT NULL,
  Pack_Qty varchar(100) DEFAULT NULL,
  Packing_Group varchar(100) DEFAULT NULL,
  Total_No_of_Packing_Group varchar(100) DEFAULT NULL,
  Mix_Group varchar(100) DEFAULT NULL,
  Mix varchar(100) DEFAULT NULL,
  BomNo varchar(100) DEFAULT NULL,
  Case_map varchar(100) DEFAULT NULL,
  Images varchar(100) DEFAULT NULL,
  CreatedBy int DEFAULT NULL,
  CreatedDate timestamp DEFAULT NULL,
  ModifiedBy int DEFAULT NULL,
  ModifiedDate timestamp DEFAULT NULL,
  Status bit DEFAULT NULL,
  PRIMARY KEY (BOM_ID)
)