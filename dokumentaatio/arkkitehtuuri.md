Sovelluksen datan muodostavat harjoitustyo.domain pakkauksen luokat User, Employee, Client ja Assignment, jotka kuvaavat käyttäjiä, työntekijöitä, asiakkaita ja työtehtäviä. Luokkien suhteet näkyvät alla olevassa luokkakaaviossa.

[Luokkakaavio](https://github.com/RoniNiklas/ot-harjoitustyo/blob/master/dokumentaatio/Luokkakaavio.jpg)

Käyttöliittymästä vastaa harjoitustyo.presentation pakkauksen oliot (tällä hetkellä Ui ja Ui.EmployeeView). Ui-luokan olio toimii pohjana, jonka päälle luodaan erillisiä olioita ihan vain luokkien koodin selattavuutta helpottamaan. Nimentä tulee muuttumaan, sillä tällä hetkellä Ui.EmployeeView - luokan olio vastaa siitä työnantajan näkymästä, missä työntekijöitä voi selata, ja nimentä ei välttämättä kaikista selkein.

Sovelluslogiikasta vastaa harjoitustyo.dao pakkauksen oliot (tällä hetkellä AppUserManagerDao ja EmployeeManagerDao). Tämän pakkauksen oliot vastaavat käyttöliittymän tarvitseman datan käsittelystä; tallentamisesta, hakemisesta, muokkaamisesta ja poistamisesta. Tällä sovelluksella on vain muistiin tallettavia olioita AppUserManagerMemory ja EmployeeManagerMemory. Jatkossa olisi toivomus, että saisin jonkinlaisen persistentin databasen käyttöön, mutta en onnistunut saamaan springin jparepoja toimimaan ilman, että pyörittäisin palvelinta, ja db:n käsittely localhostiin tehtävillä http posteilla ja geteillä tuntui työläältä, niin en siihen enempää syventynyt.

