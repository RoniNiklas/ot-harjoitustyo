Sovelluksen datan muodostavat harjoitustyo.domain pakkauksen luokat User, Employee, Client ja Assignment, jotka kuvaavat käyttäjiä, työntekijöitä, asiakkaita ja työtehtäviä. Luokkien suhteet näkyvät alla olevassa luokkakaaviossa.

[Luokkakaavio](https://github.com/RoniNiklas/ot-harjoitustyo/blob/master/dokumentaatio/Luokkakaavio.jpg)

Alla sekvenssikaavio adminin kirjautumiselle muistipohjaisessa versiossa sovelluksesta.  

[Sekvenssikaavio adminin sisään kirjautumiselle](https://github.com/RoniNiklas/ot-harjoitustyo/blob/master/dokumentaatio/sekvenssikaavio.png)

Käyttöliittymästä vastaa Main ja harjoitustyo.presentation pakkauksen oliot (tällä hetkellä Ui ja Ui.EmployeeView). Main olio toimii pohjana, jonka päälle luodaan erillisiä olioita ihan vain luokkien koodin selattavuutta helpottamaan. Nimentä tulee muuttumaan, sillä tällä hetkellä EmployeeView - luokan olio vastaa siitä työnantajan näkymästä, missä työntekijöitä voi selata, ja nimentä ei välttämättä kaikista selkein.

Sovelluslogiikasta vastaa harjoitustyo.dao pakkauksen interfacet appUserManagerDao, employeeManagerDao ja ClientManagerDao.

Tietokannasta vastaa harjoitustyo.repository luokan oliot.
