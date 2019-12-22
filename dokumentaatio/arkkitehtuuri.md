## Sovelluksen rakenne
Sovelluksen datan muodostavat harjoitustyo.domain pakkauksen luokat User, Employee, Client ja Assignment, jotka kuvaavat käyttäjiä, työntekijöitä, asiakkaita ja työtehtäviä. Luokkien suhteet näkyvät alla olevassa luokkakaaviossa.

[Luokkakaavio](https://github.com/RoniNiklas/ot-harjoitustyo/blob/master/dokumentaatio/LuokkakaavioUusi.jpg)

Käyttöliittymästä vastaa Main ja harjoitustyo.presentation pakkauksen oliot (tällä hetkellä Ui ja Ui.EmployeeView). Main olio toimii pohjana, jonka päälle luodaan erillisiä olioita ihan vain luokkien koodin selattavuutta helpottamaan. Nimentä tulee muuttumaan, sillä tällä hetkellä EmployeeView - luokan olio vastaa siitä työnantajan näkymästä, missä työntekijöitä voi selata, ja nimentä ei välttämättä kaikista selkein.

Sovelluslogiikasta vastaa harjoitustyo.dao pakkauksen interfacet appUserManagerDao, employeeManagerDao ja ClientManagerDao sekä näitä toteuttavat luokat

Tietokannasta vastaa harjoitustyo.repository luokan oliot AppUserRepository, ClientRepository, EmployeeRepository ja AssignmentRepository.

Alla esimerkki ohjelman end-to-end toiminnasta. Kuvassa on sekvenssikaavio adminin kirjautumiselle sovellukseen sisään ja sovelluksesta ulos.

[Sekvenssikaavio adminin sisään kirjautumiselle](https://github.com/RoniNiklas/ot-harjoitustyo/blob/master/dokumentaatio/sekvenssikaavioUusi.png)

Käyttäjä täyttää kirjautumistiedot ja painaa login nappulaa (tai enteriä). Tällöin käyttöliittymä kutsuu AppUserManagerDB-luokan checkLogin-metodia, jolla varmistetaan kirjautumistietojen oikeus. CheckLogin-metodi hakee AppUserRepositorysta käyttäjänimellä käyttäjän, varmistaa tälläisen olevan olemassa, ja vertaa syötettyä salasanaa tallennetun käyttäjän salasanaan, ja palauttaa boolean arvon. Jos arvo on tosi, niin käyttöliittymä pyytää AppUserManagerDB-luokkaa hakemaan käyttäjän AppUserRepositorystä. Sen jälkeen käyttäjän authorization-tarkistetaan, ja "Admin"-tason käyttäjille avataan admin-näkymä kutsumalla UI-luokan createAdminView()-metodia. Samalla suljetaan sisäänkirjautumisnäkymä, ja avataan uloskirjautumisnäkymä. Uloskirjauduttaessa poistetaan admin-näkymän eri osat, ja luodaan sisäänkirjautumisnäkymä uudelleen.  
  
## Ohjelman rakenteeseen jääneet heikkoudet
Ohjelmassa on paljon toisteista koodia. Employee ja Client luokat (ja näitä käsittelevät dao, presentation ja repository-luokat) ovat lähes täysin toistensa kopioita. En kumminkaan saanut fiksattua niitä abstraktin super-luokan alle, sillä JPArepositoryn toiminnan mukauttaminen tälläiseen ei onnistunut halutulla aikataululla.   

Employee ja Client luokat kutsuvat Assignmentteja fetchtype.EAGER:illä, mikä aiheuttaa suhtkoht ison määrän tarpeettomia tietokantakutsuja. LAZY:n käyttäminen johti kumminkin ongelmiin tietyissä käyttötapauksissa, enkä löytänyt muuta ratkaisua.  

Käyttöliittymän näkymän voisi luoda FXML-tiedostoilla java-koodin sijaan, sillä nyt nämä käyttöliittymä-luokat ovat melko massiivisia ja vaikeaselkoisia parhaista yrityksistäni huolimatta. Näkymän siirtäminen FXML:ään ja sen controllerin pyörittäminen Javalla olisi hyvä idea.  
