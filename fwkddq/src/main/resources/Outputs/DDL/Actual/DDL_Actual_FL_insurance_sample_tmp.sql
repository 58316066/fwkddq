DROP TABLE IF EXISTS `FL_INSURANCE_SAMPLE`; 

CREATE TABLE `FL_INSURANCE_SAMPLE` ( 
`POLICYID` varchar(6), 
`STATECODE` varchar(2), 
`COUNTY` varchar(19), 
`EQ_SITE_LIMIT` varchar(10), 
`HU_SITE_LIMIT` varchar(10), 
`FL_SITE_LIMIT` varchar(10), 
`FR_SITE_LIMIT` varchar(10), 
`TIV_2011` varchar(10), 
`TIV_2012` varchar(10), 
`EQ_SITE_DEDUCTIBLE` varchar(9), 
`HU_SITE_DEDUCTIBLE` varchar(9), 
`FL_SITE_DEDUCTIBLE` varchar(8), 
`FR_SITE_DEDUCTIBLE` varchar(6), 
`POINT_LATITUDE` varchar(9), 
`POINT_LONGITUDE` varchar(10), 
`LINE` varchar(11), 
`CONSTRUCTION` varchar(19), 
`POINT_GRANULARITY` varchar(1), 
`NEWLINE` varchar(6)
 );