# Vaatimusmäärittely

## Sovelluksen tarkoitus

Ideana yksinkertainen työnhallintasovellus, jolla yritys pystyy hallinnoimaan työntekijöidensä työkeikkoja, koska satuin näkemään joskus yhden yrityksen pyynnön, että joku jonkun tälläisen heille tuottaisi. Paljon vastaavia on jo olemassa, mutta en nyt keksi muutakaan tekemistä.

## Perusversion tarjoama toiminnallisuus

Työnantaja    
    - Voi kirjautua sisään ja ulos.
    - Voi lisätä asiakkaita   
        - Attribuutteina id (pk), nimi (string), osoite (string), puh (string), sahkoposti (string)   
    - Voi lisätä työntekijöitä   
        - Attribuutteina id (pk), nimi (string), osoite (string), puh (string), sahkoposti (string), käyttäjätunnus(string), salasana(string)   
    - Voi lisätä keikkoja   
        - Attribuutteina id (pk), asiakas (fk), työntekijä (fk), keikan kuvaus (string), keikka tehty/eitehty (boolean), keikkaraportti (luodaan keikan jälkeen)(string), keikan alku (date), keikan loppu (date).   
    - Voi liittää asiakkaita keikkoihin   
    - Voi liittää työntekijöitä keikkoihin   
        - Työntekijälle ei voi liittää päällekkäisiä keikkoja.   

Työntekijä   
    - Voi kirjautua sisään ja ulos.
    - Voi nähdä tulevat ja menneet keikkansa.   
    - Voi kuitata keikkoja tehdyiksi.   
    - Voi täyttää keikkaraportteja, kun keikka tehty.   
    - Voi updatea keikan aloitus/lopetusajankohdan, kun keikka tehty.   

Asiakkaalla   
    - Ei pääsyä sovellukseen.   

### Ennen kirjautumista   

Vain kirjautumisikkuna, josta voi kirjautua. Ei pääsyä mihinkään ilman autentikaatiota. Ainoastaan työnantajakäyttäjä voi luoda uusia käyttäjiä (työntekijöitä).   

Kirjautumisikkuna    
    => Login(käyttäjätunnus, salasana)   
    => Close program   
    
### Kirjautumisen jälkeen   

Kirjauduttuaan näkymät perustuen autentikaatioon. 
   
Työnantajalla    

    Komento =>    
        Asiakas   
            => Lisää asiakas(nimi, osoite, puh, sahkoposti) => lisää asiakas tietokantaan, return void   
            => Hae asiakkaat() => return list(asiakas)   
            => Hae asiakkat(string) => tarkistaa, jos mistään asiakkaan attribuutista löytyy string, filteröi, return list(asiakas)   
            => Muokkaa asiakasta(id) => hakee asiakkaan id:llä. Jos asiakas on olemassa,  pyytää muokattavia kohtia, empty jättää muuttamatta, return void   
        Työntekijä   
            => Lisää työntekijä(nimi, osoite, puh, sahkoposti) => lisää työntekijä tietokantaan, return void   
            => Hae työntekijät() => return list(työntekijä)   
            => Hae työntekijät(string) => tarkistaa, jos mistään työntekijän attribuutista löytyy string, filteröi, return list(työntekijä)   
            => Muokkaa työntekijä (id) => hakee työntekijän id:llä. Jos työntekijä on olemassa, pyytää muokattavia kohtia, empty jättää muuttamatta, return void   
        Keikka   
            => Lisää keikka(asiakas, työntekijä, kuvaus, alku, loppu) => Lisää keikka tietokantaan, return void   
            => Hae keikat() => return list(keikka)   
            => Hae keikat(string) => hakee keikat, joissa jossain attribuutissa string (esim. työntekijän tai asiakkaan nimi), return list(keikka)      
            => Muokkaa keikkaa(id) => Hakee id:llä keikan. Jos keikka on olemassa, kysyy muutoksia, empty jättää muuttamatta, tallentaa tietokantaan, return void    
        Logout
            => kirjautuu ulos, return void.
            
Työntekijällä   

    Komento =>   
        Hae keikat() => hakee työntekijän keikat, return list(keikka)   
        Hae keikat(string) => hakee keikat, joissa jossain attribuutissa string (esim. työntekijän tai asiakkaan nimi), return list(keikka);   
        Hae uudet keikat() => hakee työntekijän keikat, joita ei tehty, return list(keikka)   
        Hae vanhat keikat() => hakee työntekijän tehdyt keikat, return list(keikka)   
        Täytä keikkaraportti(id) => Hakee id:llä keikan. Jos keikka on olemassa, kysyy muutokset (keikkaraportti, alku, loppu), tallentaa muutokset tietokantaan.   
        Logout => kirjautuu ulos, return void.
  
   
## Käyttöliittymäluonnos

En osaa javan graafisia käyttöliittymähommia ollenkaan. Toteutan alustavasti tekstikäyttöliittymällä. Luonnos joistain käyttäjän komennoista tai komentoja toteuttavista metodeista yllä.  

## Jatkokehitysideoita

Graafinen käyttöliittymä.   
Asiakkaiden, keikkojen ja työntekijöiden poisto.   
Työntekijä voi muokata omia tietojaan.   
Työntekijälle kanssa palkkatiedot (tehdyt tunnit, tuntipalkka, veroprosentti) ja palkanlaskennan liittäminen sovellukseen.   
Asiakkaalle mahdollisuus antaa kommentteja keikasta(?)   


