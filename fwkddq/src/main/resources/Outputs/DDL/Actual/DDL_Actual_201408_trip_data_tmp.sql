DROP TABLE IF EXISTS `201408_TRIP_DATA`; 

CREATE TABLE `201408_TRIP_DATA` ( 
`TRIP_ID` varchar(6), 
`DURATION` varchar(6), 
`START_DATE` varchar(15), 
`START_STATION` varchar(45), 
`START_TERMINAL` varchar(2), 
`END_DATE` varchar(15), 
`END_STATION` varchar(45), 
`END_TERMINAL` varchar(2), 
`BIKE__` varchar(3), 
`SUBSCRIBER_TYPE` varchar(10), 
`ZIP_CODE` varchar(11), 
`NEWLINE` varchar(7)
 );