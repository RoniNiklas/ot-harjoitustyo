
## KÄYTTÖ

Lataa tiedosto: [release](https://github.com/RoniNiklas/ot-harjoitustyo/releases/tag/1.0.0)   
Ohjelma käsittelee database nimistä tiedostoa, joten pyöritä ohjelmaa kansiossa, jossa vastaavaa ei ole. Ohjelma luo tiedoston, jos sitä ei ole olemassa.

## Aloita käskyllä

Sovellus käynnistetään alla olevalla cmdline-komennolla siinä kansiossa, mihin .jar tiedoston latasit.

java -jar Harjoitustyo-0.jar

## Kirjautuminen  

Täytä käyttäjätiedot   
Käyttäjä: Admin  
Salasana: Password  

## Kirjautumisen jälkeen  

Uloskirjautuminen onnistuu painamalla logout nappulaa näkymän yläosassa.  

Vasemmassa reunassa näkyy nappulat Employees, Clients ja Assignments. Niitä painelemalla vaihtuu käyttöliittymän keskellä oleva osio.  

## Employee-näkymä

Employees-nappulalla saat näkyviin työntekijöiden muokkaamiseen tarkoitetun käyttöliittymän. Ylimpänä näkyy taulukko, jossa kaikki firman nykyiset työntekijät. Työntekijöiden National Identity Number (HeTu) on uniikki.

Taulukon soluja tuplaklikkaamalla voit muokata työntekijän tietoja.  

Taulukon alla on työntekijöiden filtteröinti-toiminnallisuus, jossa voit syöttää tekstilaatikkoon tekstiä, ja Filter Employees-nappulalla rajoittaa taulukossa näkyviä työntekijöitä tekstilaatikon tekstin pohjalta. Clear Filter nappula tyhjentää tekstilaatikon ja palauttaa kaikki työntekijät näkyviin.   

Filtterin alla on uusien työntekijöiden-lisäämisboxi, johon voit täyttää tekstilaatikkoihin tietoja, ja sitten luoda uuden työntekijän painamalla Add Employee. Ohjelma tarkistaa, että uuden työntekijän HeTua ei ole jo olemassaolevalla työntekijällä. 

Työntekijänlisäämisboxin alla on työntekijän poistamisboxi. Täytä tekstilaatikkoon työntekijän HeTu, ja paina Remove Employee-nappulaa, ja työntekijä poistetaan tietokannasta, ja taulukko päivittyy. Työntekijän tai asiakkaan poistaminen poistaa tällä hetkellä myös heihin liittyvät työkeikat, mikä ei oikeastaan liene älykästä, joten ole varovainen poistamisien kanssa.

## Client-näkymä  

Sama kuin yllä, mutta asiakkaiden hallintaan tarkoitettu. 

## Assignments-näkymä  

Assignments näkymässä näkyy kaikki työtehtävät samalla tavalla taulussa kuin työntekijätkin, ja niitä voi filtteröidä samalla tavalla kuin työntekijöitä. Taulukon tiedoista vain keikan kuvausta, statusta, osoitetta ja contact-puhelinnumeroa voi muuttaa. Muut keikan muuttujat eivät ole muutettavissa. Työtehtäviä voi myös poistaa taulukossa olevalla delete nappulalla.

Uusia keikkoja voi luoda painamalla Create Assignment nappulaa, josta avautuu uusi ikkuna.

Ensin valitaan uudessa ikkunassa keikkaan liittyvä asiakas listasta, jota voi myös filtteröidä vieressä olevalla filtteriboxilla samalla tavalla kuin muissakin näkymissä. Näkymässä voi myös suoraan luoda uuden asiakkaan, joskin pitää muistaa painaa Create Client nappulaa, jotta asiakas luodaan ja hän siirtyy valikoitavien asiakkaiden listaan.

Sitten painetaan next, ja tehdään sama keikkaan liittyvän työntekijän suhteen.

Sitten painetaan next, ja täytetään puuttuvat keikan yksityiskohdat. Näkymässä voi muuttaa keikan osoitetta (default: asiakkaan kotiosoite), contact-puhelinnumeroa (default:asiakkaan puhelinnumero), kuvausta, alkupäivää ja -aikaa sekä loppupäivää ja -aikaa.   

Sitten painetaan submit, ja uusi ikkuna sulkeutuu, ja uuden keikan pitäisi näkyä keikkaluettelossa.  
