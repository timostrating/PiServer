
-- This is our table
CREATE TABLE `unwdmi`.`measurements` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `stn` CHAR(10) NULL COMMENT 'Het station waarvan deze gegevens zijn',
  `date` CHAR(10) NULL COMMENT 'Datum van versturen van deze gegevens, formaat: yyyy-mm-dd',
  `time` CHAR(10) NULL COMMENT 'Tijd van versturen van deze gegevens, formaat: hh:mm:ss',
  `temp` CHAR(10) NULL COMMENT 'Temperatuur in graden Celsius, geldige waardes van -9999.9 t/m 9999.9 met 1 decimaal',
  `dewp` CHAR(10) NULL COMMENT 'Dauwpunt in graden Celsius, geldige waardes van -9999.9 t/m 9999.9 met 1 decimaal',
  `stp` CHAR(10) NULL COMMENT 'Luchtdruk op stationsniveau in millibar, geldige waardes van 0.0 t/m 9999.9 met 1 decimaal\n',
  `slp` CHAR(10) NULL COMMENT 'Luchtdruk op zeeniveau in millibar, geldige waardes van 0.0 t/m 9999.9 met 1 decimaal\n',
  `visib` CHAR(10) NULL COMMENT 'Zichtbaarheid in kilometers, geldige waardes van 0.0 t/m 999.9 met 1 decimaal\n',
  `wdsp` CHAR(10) NULL COMMENT 'Windsnelheid in kilometers per uur, geldige waardes van 0.0 t/m 999.9 met 1 decimaal',
  `prcp` CHAR(10) NULL COMMENT 'Neerslag in centimeters, geldige waardes van 0.00 t/m 999.99 met 2 decimalen',
  `sndp` CHAR(10) NULL COMMENT 'Gevallen sneeuw in centimeters, geldige waardes van -9999.9 t/m 9999.9 met 1 decimaal',
  `frshtt` CHAR(6) NULL COMMENT 'Binair gevroren, geregend, gesneeuwd, gehageld, onweer, Tornado/windhoos',
  `cldc` CHAR(10) NULL COMMENT 'Bewolking in procenten, geldige waardes van 0.0 t/m 99.9 met 1 decimaal',
  `wnddir` CHAR(3) NULL COMMENT 'Windrichting in graden, geldige waardes van 0 t/m 359 alleen gehele getallen',
  PRIMARY KEY (`id`));


-- The default allowed connections are around 150
show variables like '%connections';

-- Every thread creates a connection so we require more connections to the database
set global max_connections = 900;