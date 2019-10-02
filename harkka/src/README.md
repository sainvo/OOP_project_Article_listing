**ABOUT OOPharkka**
This is a small scale practice Java project for the Object Oriented Programming course @TurkuUniversity
Author of this project is Sanna Volanen, a 2nd year IT student and MA in English Translation
**INITIAL IDEA**
Idea: Create a small database for thesis source literature, such as articles (books, ebooks etc.)

**Implementation plan: (APPROVED)**
PROJEKTISUUNNITELMA
*KATEGORIA: E*
 Aihe: yksinkertainen tietokanta opinnäyteartikkeleita varten

Luokat (Classes):
    Tietokanta (Database)
        * SQLite
        * tallennetut lähdetiedot (archived article specs)
        * artikkelin lisäys ja poisto, haku- ja listaustoiminnot (functions:add,delete,get, list)
    
    Artikkeli 
        ? ladattu artikkeli (teksti (tai URL?)), joka lisätään tietokantaan (downloaded article that will be added to DB)
        * toteuttaa Luettelon (implements Listable)
        * Tunnistetiedot (ID info)
        * Lukutiedot (data about accessing/opening)
        * Comparable?
    
    Sitaatti (Citacion)
        ? valittu tekstipätkä tietystä artikkelista (selected string from article)
        * toteuttaa Luettelon (implements Listable)
        * perii Artikkelin (inherits Article)
        * to String: default-muotoilu suora lainaus (method for returning direct quote)
    
    Luetteloitava (rajapinta) (Listable interface)
        ? listaustoiminnot (methods for listing articles/citacions)   
        * default tuorein ensin (default order newest first)
    
    Lähdeluettelo
        ? aakkostettu listaus käytetyistä artikkeleista (alphabetized listing of *cited* articles)
        * toteuttaa Luettelon (implements Listable)

**FEEDBACK FROM INSTRUCTOR**
Suunnitelma hyväksytty.

Aihe on mukavan erilainen. Yleisesti tietokannoista: vaikka periaatteessa ne tukevat kaikenlaisen datan lisäämistä, yleensä isommat tiedostot, kuten artikkelit/kuvat/jotain muuta olisi hyvä pitää kannan ulkopuolella. Toki sinulla on varmaan vain muutamia (ainakin vähemmän kuin tuhansia?) artikkeleja, joten kantaan tallentaminen ei ole välttämättä ongelma.

Käytännössä homman voisi siis toteuttaa niin, että ohjelmasi säilyttää artikkelit kansiossa ja tietokannasta löytyy ainoastaan tiedostopolku josta artikkeli löytyy. Lisäksi tietokantaan voisi sitten tallentaa avainsanoja ja tekijöiden tiedot kyseisistä artikkeleista.

En tuomitse jos tallennat myös artikkelisi siihen kantaan suoraa esim BLOB tietotyyppinä, mutta ajattelin kuitenkin mainita toisen - kenties paremman - idean.
