CREATE TABLE `contact` (
   id INT auto_increment NOT NULL,
  `email_id` varchar(100) NOT NULL,
  `phone_no` int ,
  `address` varchar(100),
  `city` varchar(100),
  `state` varchar(100),
  `country` varchar(100),
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` BOOL DEFAULT 0 NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `customer` (
	id INT auto_increment NOT NULL,
  `contact_id` int NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `middle_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `identification_number` varchar(100) NOT NULL UNIQUE,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` BOOL DEFAULT 0 NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `customer_contact_fk` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`id`)
);


CREATE TABLE account_type (
	id INT auto_increment NOT NULL,
	`type` varchar(100) NOT NULL    ,
	created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	is_deleted BOOL DEFAULT 0 NULL,
	CONSTRAINT account_type_pk PRIMARY KEY (id)
);

CREATE TABLE account (
	id INT auto_increment NOT NULL,
	account_number INT NOT NULL UNIQUE,
	customer_id INT NOT NULL,
	account_type_id INT NOT NULL,
	current_balance FLOAT NULL,
	created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	is_deleted BOOL DEFAULT 0 NULL,
	CONSTRAINT account_pk PRIMARY KEY (id),
	CONSTRAINT account_customer_FK FOREIGN KEY (customer_id) REFERENCES customer(id),
	CONSTRAINT account_account_type_FK FOREIGN KEY (account_type_id) REFERENCES account_type(id)
);