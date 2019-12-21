## Työnhallintasovellus

Ideana yksinkertainen työnhallintasovellus, jolla yritys pystyy hallinnoimaan työntekijöidensä työkeikkoja. Työnantaja voi luoda työntekijöitä, asiakkaita ja keikkoja sekä jakaa työntekijöilleen keikkoja. Työntekijät voivat katsoa missä heillä on keikkoja ja milloin. Työntekijät voivat myös merkitä keikkoa tehdyiksi ja antaa keikkaraportteja. 

## Työn dokumentaatio:     
[Työaikakirjanpito](https://github.com/RoniNiklas/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.MD)      
[Vaatimusmäärittely](https://github.com/RoniNiklas/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.MD)    
[Arkkitehtuuri](https://github.com/RoniNiklas/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

## Releaset
[Viikko5-6](https://github.com/RoniNiklas/ot-harjoitustyo/releases/tag/Viikko5)

## Komentorivi

Testit suoritetaan komennolla

mvn test

Testikattavuusraportti luodaan komennolla

mvn jacoco:report

Checkstyleraportti luodaan komennolla

mvn checkstyle:checkstyle

## TODO
1. Dokumentaatio:
- vaatimusmäärittelyn korjaaminen
  - myös tässä dokumentissa
- käyttöohjeet
- asennusohjeet
- javadoc
- testausdokumentti (?)
  - https://github.com/mluukkai/OtmTodoApp/blob/master/dokumentaatio/testaus.md
- päivitä myös viimeistään tässä vaiheessa Labtooliin projektisi nimi vastaamaan projektin aihetta.
2. Ota lombok käyttöön koko projektissa
- poista non-custom setterit ja getterit.
3. Client + Employee luokat perimään Person luokka, jotta copypaste vähäisempi. Sitten teknisesti voit luoda yhden "personView"-luokan, jota sitten extendaa employee ja client view luokat, joilla ainoana erona on se repository.
  - ja manager luokat perimään personmanagerluokka?
4. Testikattavuus
- 70% rivikattavuus, tee assignment repolle testit.
5. Sillein, että työntekijälle ei voi liittää päällekkäisiä keikkoja. Tsekkaa vaikka validate metodissa.
6. Siirrä utilsseihin niitä replaceaftersleep etc. juttuja.
7. Validoi persoonien luonti esim.
8. Final release.
7. Testaa etätyöpöydällä
