Sovelluksen datan muodostavat harjoitustyo.domain pakkauksen luokat User, Employee, Client ja Assignment, jotka kuvaavat käyttäjiä, työntekijöitä, asiakkaita ja työtehtäviä. Luokkien suhteet näkyvät alla olevassa luokkakaaviossa.

[Luokkakaavio](https://github.com/RoniNiklas/ot-harjoitustyo/blob/master/dokumentaatio/LuokkakaavioUusi.jpg)

Käyttöliittymästä vastaa Main ja harjoitustyo.presentation pakkauksen oliot (tällä hetkellä Ui ja Ui.EmployeeView). Main olio toimii pohjana, jonka päälle luodaan erillisiä olioita ihan vain luokkien koodin selattavuutta helpottamaan. Nimentä tulee muuttumaan, sillä tällä hetkellä EmployeeView - luokan olio vastaa siitä työnantajan näkymästä, missä työntekijöitä voi selata, ja nimentä ei välttämättä kaikista selkein.

Sovelluslogiikasta vastaa harjoitustyo.dao pakkauksen interfacet appUserManagerDao, employeeManagerDao ja ClientManagerDao sekä näitä toteuttavat luokat

Tietokannasta vastaa harjoitustyo.repository luokan oliot AppUserRepository, ClientRepository, EmployeeRepository ja AssignmentRepository.

Alla esimerkki ohjelman end-to-end toiminnasta. Kuvassa on sekvenssikaavio adminin kirjautumiselle sovellukseen sisään ja sovelluksesta ulos.

[Sekvenssikaavio adminin sisään kirjautumiselle](https://github.com/RoniNiklas/ot-harjoitustyo/blob/master/dokumentaatio/sekvenssikaavioUusi.png)
