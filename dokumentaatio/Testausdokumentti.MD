## Testausdokumentti   

Ohjelmaa on testattu sekä kattavasti manuaalisesti että automatisoiduin testein JUnitilla. 

## Yksikkö- ja integraatiotestaus  
## Yksikkötestit  

Yksikkötestit keskittyvät lähinnä domain-pakkauksen luokkien toiminnan varmistamiseen, ja ovat suoritettu täysin automaattisesti JUnit-kirjastolla. Nämä löytyvät harjoitustyo.unitTests-pakkauksesta.


## Integraatiotestit    

Integraatiotestit keskittyvät dao-pakkauksen luokkien toiminnan varmistamiseen. Nämä löytyvät harjoitustyo.managerTests-pakkauksesta. Testit on konfiguroitu käyttämään erillistä muistissa olevaa SQL-databasea application-test.properties tiedostolla, jotta testien toimintaan ei vaikuta minkään käytetyn tietokannan tila. Testit käyttävät kumminkin ohjelman oikeita luokkia kyseisen databasen pyörittämiseen. Missään integraatiotestissä ei ole käytetty stub- tai mock-luokkia, sillä en nähnyt tarvetta testeille, joissa sellaisia käytettäisiin. Mahdollisesti, jos olisi pitänyt myös käyttöliittymää testata, niin database olisi silloin voitu luoda stubbina tai mockina, jotta testit nopeutuisivat. (sillä databaselle on jo omat testinsä). Ohjelmassa on myös repository-interface-luokille muutama testi, mutta nämä eivät kattavia, sillä managerDB-testit testaavat jo ne toiminnot, joita repositoryilta vaaditaan.

## Testauskattavuus  

Käyttöliittymäkerrosta lukuunottamatta sovelluksen testauksen rivikattavuus on 79% ja haarautumakattavuus 92%. Kattavuutta vähentää domain-pakkauksen luokkien osalta mm. lombok-kirjaston @Setter annotaation mukana tulevat setterit, joita ohjelma ei käytä mihinkään, ja joita en jaksa testata. En kumminkaan halunnut luopua annotaatioista, sillä ne vähentävät boilerplatea niiden attribuuttien kannalta, joille tarvitaan settereitä. Samaten assignmenttein poistamiseen clienteilta/employeelta liittyvät toiminnot ovat testaamatta, sillä niitä käytetään, kun poistetaan client/employee, jolla on assignmentteja, enkä tiennyt haluanko sallia sellaista tehtävän. Nykyisessä versiossa kumminkin näin voi tehdä, mutta toimintoa ei testata automaattisesti.

## Järjestelmätestaus

Käyttöliittymätestaus on tehty manuaalisesti. 

## Asennus ja konfigurointi   

Sovelluksen automaattisest testit on pyöritetty Windows 7-ympäristössä sekä Java 8 että Java 11 versioilla, ja testattu Java 11 versiota manuaalisesti etätyöpöydällä.

Sovellusta on testattu sekä silloin kun se luo database-tiedoston itse, että kun se käyttää jo aiemmin luomaansa database-tiedostoa.

## Toiminnallisuudet  
Kaikki määrittelydokumentin ja käyttöohjeen listaamat toiminnallisuudet on käyty läpi. Kaikkien toiminnallisuuksien yhteydessä on syötekentät yritetty täyttää myös virheellisillä arvoilla kuten tyhjillä, joskin esimerkiksi asiakkaiden ja työntekijöiden luonnissa ei ole validointia, joka estäisi tyhjien nimien tai numeroiden käyttämisen arvoina. Ainoastaan se, että kahdella työntekijällä/asiakkaalla ei voi olla samaa HeTua tarkistetaan. Keikkojen luomisessa on vahvempi validointi tyhien osalta, ja päiväyksien osalta, joskin siinäkin voi täyttää hölmöjä arvoja, kuten kirjaimia puhelinnumeroiksi jne. Nämä laskettavissa ohjelman laatuongelmaksi.
