# Vaatimusmäärittely

## Sovelluksen tarkoitus

Ideana yksinkertainen työnhallintasovellus, jolla yritys pystyy hallinnoimaan työntekijöidensä työkeikkoja, koska satuin näkemään joskus yhden yrityksen pyynnön, että joku jonkun tälläisen heille tuottaisi. Paljon vastaavia on jo olemassa, mutta en nyt keksi muutakaan tekemistä.

## Perusversion tarjoama toiminnallisuus

Työnantaja    
    - Voi kirjautua sisään ja ulos. **TEHTY**  
    - Voi lisätä työntekijöitä   **TEHTY**, paitsi että ei tee työntekijälle käyttäjää   
        - Attribuutteina id (pk), nimi (string), osoite (string), puh (string), sahkoposti (string), käyttäjätunnus(string), salasana(string)   
    - Voi selailla työntekijöitä   **TEHTY**   
    - Voi muuttaa työntekijöiden tietoja **TEHTY**, EI TEE DB:SEEN  
    - Voi poistaa työntekijöitä **TEHTY**   
    - Voi lisätä asiakkaita   **TEHTY**  
        - Attribuutteina id (pk), nimi (string), osoite (string), puh (string), sahkoposti (string)   
    - Voi poistaa asiakkaita **TEHTY**  
    - Voi muuttaa asiakkaiden tietoja **TEHTY**, EI TEE DB:SEEN  
    - Voi lisätä keikkoja   
        - Attribuutteina id (pk), asiakas (fk), työntekijä (fk), keikan kuvaus (string), keikka tehty/eitehty (boolean), keikkaraportti (luodaan keikan jälkeen)(string), keikan alku (date), keikan loppu (date).
    - Voi liittää asiakkaita keikkoihin   
    - Voi liittää työntekijöitä keikkoihin   
        - Työntekijälle ei voi liittää päällekkäisiä keikkoja.   


Työntekijä   
    - Voi kirjautua sisään ja ulos.    **TEHTY**  
    - Voi nähdä tulevat ja menneet keikkansa.   
    - Voi kuitata keikkoja tehdyiksi.   
    - Voi täyttää keikkaraportteja, kun keikka tehty.   
    - Voi updatea keikan aloitus/lopetusajankohdan, kun keikka tehty.   

## Käyttöliittymäluonnos

En osaa javan graafisia käyttöliittymähommia ollenkaan. Toteutan alustavasti tekstikäyttöliittymällä. Luonnos joistain käyttäjän komennoista tai komentoja toteuttavista metodeista yllä.  

## Jatkokehitysideoita

Graafinen käyttöliittymä.   
Asiakkaiden, keikkojen ja työntekijöiden poisto.   
Työntekijä voi muokata omia tietojaan.   
Työntekijälle kanssa palkkatiedot (tehdyt tunnit, tuntipalkka, veroprosentti) ja palkanlaskennan liittäminen sovellukseen.   
Asiakkaalle mahdollisuus antaa kommentteja keikasta(?)   


