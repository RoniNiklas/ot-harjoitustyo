##Testausdokumentti
Ohjelmaa on testattu sekä kattavasti manuaalisesti että automatisoiduin testein JUnitilla. 

## Yksikkö- ja integraatiotestaus  
## Yksikkötestit  

Yksikkötestit keskittyvät lähinnä domain-pakkauksen luokkien toiminnan varmistamiseen, ja ovat suoritettu täysin automaattisesti JUnit-kirjastolla. Nämä löytyvät harjoitustyo.unitTests-pakkauksesta.


##Integraatiotestit  

Integraatiotestit keskittyvät dao-pakkauksen luokkien toiminnan varmistamiseen. Nämä löytyvät harjoitustyo.managerTests-pakkauksesta. Testit on konfiguroitu käyttämään erillistä muistissa olevaa SQL-databasea application-test.properties tiedostolla, jotta testien toimintaan ei vaikuta minkään käytetyn tietokannan tila. Testit käyttävät kumminkin ohjelman oikeita luokkia kyseisen databasen pyörittämiseen. Missään integraatiotestissä ei ole käytetty stub- tai mock-luokkia, sillä en nähnyt tarvetta testeille, joissa sellaisia käytettäisiin. Mahdollisesti, jos olisi pitänyt myös käyttöliittymää testata, niin database olisi silloin voitu luoda stubbina tai mockina, jotta testit nopeutuisivat. (sillä databaselle on jo omat testinsä).

##Testauskattavuus
Käyttöliittymäkerrosta lukuunottamatta sovelluksen testauksen rivikattavuus on 79% ja haarautumakattavuus 92%. Kattavuutta vähentää domain-pakkauksen luokkien osalta mm. lombok-kirjaston @Setter annotaation mukana tulevat setterit, joita ohjelma ei käytä mihinkään, ja joita en jaksa testata. En kumminkaan halunnut luopua annotaatioista, sillä ne vähentävät boilerplatea niiden attribuuttien kannalta, joille tarvitaan settereitä. Samaten assignmenttein poistamiseen clienteilta/employeelta liittyvät toiminnot ovat testaamatta, sillä niitä käytetään, kun poistetaan client/employee, jolla on assignmentteja, enkä tiennyt haluanko sallia sellaista tehtävän. Nykyisessä versiossa kumminkin näin voi tehdä, mutta toimintoa ei testata.



Testaamatta jäivät tilanteet, joissa käyttäjät tai tehtävät tallettavia tiedostoja ei ole, tai niihin ei ole luku- ja kirjoitusoikeutta.

Järjestelmätestaus
Sovelluksen järjestelmätestaus on suoritettu manuaalisesti.

Asennus ja konfigurointi
Sovellus on haettu ja sitä on testattu käyttöohjeen kuvaamalla tavalla sekä OSX- että Linux-ympäristöön siten, että sovelluksen käynnistyshakemistossa on ollut käyttöohjeen kuvauksen mukainen config.properties-tiedosto.

Sovellusta on testattu sekä tilanteissa, joissa käyttäjät ja työt tallettavat tiedostot ovat olleet olemassa ja joissa niitä ei ole ollut jolloin ohjelma on luonut ne itse.

Toiminnallisuudet
Kaikki määrittelydokumentin ja käyttöohjeen listaamat toiminnallisuudet on käyty läpi. Kaikkien toiminnallisuuksien yhteydessä on syötekentät yritetty täyttää myös virheellisillä arvoilla kuten tyhjillä.

Sovellukseen jääneet laatuongelmat
Sovellus ei anna tällä hetkellä järkeviä virheilmoituksia, seuraavissa tilanteissa

konfiguraation määrittelemiin tiedostoihin ei ole luku/kirjoitusoikeuksia
