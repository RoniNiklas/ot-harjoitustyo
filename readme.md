## Työnhallintasovellus

Ideana yksinkertainen työnhallintasovellus, jolla yritys pystyy hallinnoimaan työntekijöidensä työkeikkoja. Työnantaja voi luoda työntekijöitä, asiakkaita ja keikkoja.

Huom. Ohjelman source githubissa on Java 8 versio. Erillinen Java 11:lla pyörivä .jar release löytyy alta. Erona vain pom.xml:ssä määritellyt dependencyt, ja sit erillinen NewMain-luokka, joka avaa Main-luokan. Java 11 release toimi kun kokeilin etätyöpöytäyhteydellä.

## Työn dokumentaatio:     
[Vaatimusmäärittely](https://github.com/RoniNiklas/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.MD)    
[Arkkitehtuuri](https://github.com/RoniNiklas/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)   
[Käyttöohjeet](https://github.com/RoniNiklas/ot-harjoitustyo/blob/master/dokumentaatio/k%C3%A4ytt%C3%B6ohjeet.MD)  
[Testausdokumentti](https://github.com/RoniNiklas/ot-harjoitustyo/blob/master/dokumentaatio/Testausdokumentti.MD)  
[Työaikakirjanpito](https://github.com/RoniNiklas/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.MD)    

## Releaset
[Viikko5-6](https://github.com/RoniNiklas/ot-harjoitustyo/releases/tag/Viikko5)   

[Final( Java 8 )](https://github.com/RoniNiklas/ot-harjoitustyo/releases/tag/1.0.0)  
[Final( Java 11 )](https://github.com/RoniNiklas/ot-harjoitustyo/releases/tag/1.0.1)  

## Komentorivi

Testit suoritetaan komennolla

mvn test

Testikattavuusraportti luodaan komennolla

mvn jacoco:report

Checkstyleraportti luodaan komennolla

mvn checkstyle:checkstyle

.Jar tiedosto luodaan komennolla  

mvn package   

Javadoccia ei voi luoda, sillä ei ole aikaa korjata.
