# Beschrijving app

Deze app heeft als doel gevorderde schakers te laten oefenen met blindschaken. De app maakt gebruik van schaakpuzzels. Hierbij wordt een schaakpuzzel verkregen via de API. Deze schaakpuzzel heeft 1 oplossing waarbij de gebruiker een aantal zetten moet doen om de puzzel op te lossen. De app slaat verder per gebruiker op hoeveel tijd deze heeft besteed aan de puzzels. Ook wordt er opgeslagen hoeveel puzzels de gebruiker goed/fout heeft beantwoord.

![blindfoldchess_1](https://user-images.githubusercontent.com/36193067/41974752-18de5630-7a19-11e8-92d9-aa6266cebb10.png)

# Technische vormgeving

Allereerst zal er een korte beschrijving van de structuur van de app gegeven worden. Als de app geopend wordt verschijnt een menu waarin ingelogd kan worden met e-mail en wachtwoord. Indien de gebruiker nog geen account heeft kan deze doorschakelen naar het registreerscherm. Op dat scherm kan vervolgens een account aangemaakt worden.

Indien de gebruiker inlogt verschijnt het activity_menu. Dit is een menu waarin de gebruiker kan doorschakelen naar een schaakpuzzel, de statistieken of de instructie. In de instructie staat beschreven hoe de schaakpuzzel ingevuld moet worden door de gebruiker met behulp van notatie. Als de gebruiker doorschakelt naar statistieken ziet deze hoeveel puzzels er correct en fout zijn beantwoord. Ook is er weergeven hoeveel minuten een gebruiker aan het maken van de schaakpuzzels heeft besteed. Een schaakpuzzels weergeeft een schaakpuzzel die uiteindelijk goed of fout beantwoord wordt.

# Uitgebreide uitleg werking app

### Activity_main

In deze activity is het de bedoeling voor de gebruiker om in te loggen. De gebruiker kan hier zijn email en bijbehorende wachtwoord invullen.
Indien er dan op sign in geklikt wordt gebeurd er het volgende. Allereerst wordt via de functie logIn het ingevulde email en wachtwoord verkregen. Indien één van de twee velden leeg is, wordt er een toast gemaakt waarin duidelijk wordt gemaakt aan de gebruiker dat deze velden leeg zijn. Als de velden allebei ingevuld zijn maar email en wachtwoord niet matchen wordt een toast gegeven. Als de velden echter wel matchen dan is de log in succesvol en wordt de activity_menu geopend.

Een andere button die de gebruiker kan indrukken is de register button. Indien dit gebeurd, wordt de gebruiker genavigeerd naar de activity_register.

### Activity_register
In deze activity is het mogelijk voor een gebruiker zich te registreren. Er kan een e-mail ingevuld worden met wachtwoord en bevestiging van het wachtwoord. Indien de gebruiker op register drukt wordt de functie registerNow aangeroepen. In deze functie worden de mail en wachtwoorden verkregen. Indien de wachtwoorden niet matchen wordt dit aangegeven in een toast. Als de email en/of het wachtwoord(1) niet ingevuld is wordt dit aangegeven in een toast. Anders kan er geprobeerd geregistreerd te worden. Als dit niet lukt wordt er opnieuw een toast gegeven. Als er echter wel een succesvolle registratie is dan wordt een database voor de gebruiker gemaakt. Via de functie addToDB(user) wordt correct, wrong en time op nul gezet. Als laatste wordt activity_main geopend.

### Activity_menu
In deze activity krijgt de gebruiker een overzicht van de activities waarnaar deze kan navigeren. Indien de gebruiker klikt op instruction wordt deze genavigeerd naar de activity_instruction. Als de gebruiker klikt op statistics wordt deze genavigeerd naar activity_statistics. Als laatste kan er geklikt worden op puzzle om te navigeren naar activity_chess_exercise.

### Activity_instruction
In deze activity wordt een instructie van notatie gegeven aan de hand van een aantal voorbeelden. Er wordt er hierbij vanuit gegaan dat de gebruiker de notatie al kent. Dit is namelijk bij vrijwel alle gevorderde schakers het geval. 

De notatie is een middel om zetten te communiceren zonder het bord te tekenen. De notatie is onder meer verplicht voor amateurs en profs om te gebruiken tijdens toernooien of andere partijen. De notatie is dus voor deze app erg handig om te gebruiken aangezien het blindschaken geen bord kent en het alom bekend is.

### Activity_statistics
In deze activity worden de statistieken gepresenteerd van een gebruiker. Wanneer men de activity ingaat wordt onmiddellijk de functie readFromDB() aangeroepen. In deze functie wordt de user verkregen met bijbehorend id. Vervolgens worden onder deze id de waarden van de Score.class verkregen. Vervolgens wordt via de functie setText een aanpassing gedaan van de textviews. Hierbij wordt de tekst gezet op correcte antwoorden, foute antwoorden en tijd besteed aan puzzels in minuten. Deze gegevens zijn  dus verkregen uit de Score.class met behulp van de database van Firebase.

##### Score.class
Deze class heeft strings voor correct, wrong en time. Deze waardes kunnen worden aangepast en verkregen.

### Activity_chess_exercise
In deze activity kunnen schaakpuzzels opgelost worden. De activity kent meer dan 1000 regels en verwijst ook nog naar meer dan 400 regels in de ChessExerciseFunctions class. Dit maakt de activity dan ook zeer gecompliceerd. 

Wanneer de activity verschijnt wordt allereerst de functie getPuzzleNow aangeroepen. Wanneer deze wordt aangeroepen wordt het tekstvak waarin de gebruiker zijn zetten invoert (notationInText) leeg gemaakt. Er is alleen een hinttekst te zien namelijk move. Vervolgens wordt er geïndexeerd dat de puzzel niet correct en niet fout is maar dat wel nog kan worden. Vervolgens wordt de starttijd bepaald om te meten hoe lang er over de puzzel wordt gedaan. Hierna wordt de puzzle verkregen via de class GetPuzzle.

Nu de puzzel verkregen is wordt de fen die in de API zit gesplit in kleur en positie met fenStringSplit. De fen weergeeft namelijk de beginpositie en de beginkleur van de puzzel. fenToString schrijft fen dan vervolgens naar een string van 64 (= aantal schaakvelden) elementen om voor elke vakje te bepalen welk stuk erin moet staan. 

Functie returnBoardInText gebruikt de fen om de beginstelling van het bord in notatie weer te geven voor TextView notationStartPosition. Ook createbord gebruikt de fen maar ook fenToString om het bord te visualiseren in de beginstelling. Verder worden met changeText2 de TextViews displayMoves en displayMoves2 geïnitialiseerd, changeColourPieceClickers verandert de kleur van de stukken: ImageView aa t/m af. Als laatste wordt de functie obtainChessMoves uit ChessExerciseFunctions class aangeroepen om de zetten uit de pgn van de API te scheiden.

Nu de puzzel verkregen is wordt zet de functie initialiseClickers() klikkers voor de gridlayoutClickers verkregen. De gebruiker kan dan op de schaakstukken, coördinaten, = en rokadenotatie klikken om de TextView notationInText aan te passen. Zo komt er in notationText een P (Q) te staan indien op pion (Dame) geklikt wordt. 

Nu de puzzel is verkregen en de gebruiker door middel van klikken zetten kan invullen in notationInText wordt de confirm button belangrijk. Indien de gebruiker op confirm klikt wordt in confirmMove(View view) gecheckt of de door de gebruiker ingevulde zet gelijk is aan het antwoord. Als dit niet zo is wordt met de functie readFromDB de tijd van de puzzel opgeteld in firebase ook wrong gaat één omhoog. Merk op dat per puzzel er maar slechts éénmaal wrong=+1 gerekend kan worden. Vervolgens verandert de imageview result in rood. 

Als de gebruiker een goed antwoord geeft worden door middel van makeChessMove de coördinaten oftewel de stelling van het bord aangepast. Vervolgens wordt makeChessMove2() toegepast. Deze functie check allereerst of de functie is opgelost en in één keer goed is geweest. Correct wordt via readFromDB +1 (ook time wordt aangepast) en imageview result wordt groen. Ook kan het zijn dat de puzzel eerst fout was maar nu goed dan wordt alleen de time aangepast en imageview result wordt geel. Voor beide gevallen geldt dat de correctPuzzleIndex = 3, hiermee wordt aangegeven dat de puzzel is afgesloten. Indien makeChessMove2() gebeurt er niks mee. Het laatste dat makeChessMove2() kan doen is een tegenzet indien de puzzel niet klaar is.

Wanneer de button hint wordt aangeklikt wordt makeChessMove2() tweemaal aangeroepen. Er wordt een zet voor de gebruiker en een tegenzet gedaan. 

Button new puzzle roept getPuzzleNow() aan.

De laatste button show board laat de gridlayoutBoard verschijnen voor drie seconden. De gridlayoutClickers, notationStartPosition, displayMoves en displayMoves2 verdwijnen voor dezelfde tijdsduur. Dit gebeurd met de functie showBoard() die op zijn beurt disappear, emerge1 en emerge2 aanroept.


##### GetPuzzle.class
In deze class wordt er vanaf https://api.chess.com/pub/puzzle/random een api verkregen. Deze structuur van api ziet er als volgt uit: 

{"title":"An Eye For An Eye","url":"https://www.chess.com/forum/view/daily-puzzles/6-30-2017-an-eye-for-an-eye","publish_time":1498806000,"fen":"6k1/r6p/pp1p2p1/2pP2q1/P1Pb4/4B3/1P1Q2PP/5RK1 w - - 0 1","pgn":"[Date \"????.??.??\"]\r\n[Result \"*\"]\r\n[FEN \"6k1/r6p/pp1p2p1/2pP2q1/P1Pb4/4B3/1P1Q2PP/5RK1 w - - 0 1\"]\r\n\r\n1. Qxd4 cxd4 2. Bxg5\r\n*","image":"https://www.chess.com/dynboard?fen=6k1/r6p/pp1p2p1/2pP2q1/P1Pb4/4B3/1P1Q2PP/5RK1%20w%20-%20-%200%201&size=2"}.

# Uitdagingen/ontwikkelingen gedurende ontwikkeling app

## Uitdaging
De grootste uitdaging van deze app is het vertalen van de zetten in korte notatie naar het bord. Indien er bijvoorbeeld een zet wordt gegeven als Be4 oftewel de loper moet naar veld e4 dan is het nog niet duidelijk waar de loper precies vandaan komt. Hierdoor is het noodzakelijk over het bord te itereren, hierbij alle lopers te selecteren en vervolgens de loper die naar dat veld kan te laten verdwijnen en te laten verschijnen op veld e4. Hierbij is het zodanig gecodeerd dat een loper niet door andere stukken heen kan (velden bestemming en vertrekpunt zijn leeg) ook mag deze alleen schuin bewegen. Dit moet voor alle stukken gedaan worden en heeft heel veel tijd gekost. Ik heb hier echter wel veel van geleerd aangezien het ontzettend veel structuur eist om dit efficiënt en goed te programmeren.

## Ontwikkeling
De veranderingen die ik heb gemaakt zijn als volgt. Allereerst is er in het menuveld een extra button gekomen. Deze button navigeert de gebruiker naar de instructie. Ten tweede is de activity_statistics wat aangepast. De gegevens die weergeven worden zijn namelijk aantal correct aantal fout en totale tijd besteed. Als laatste is de activity_chess_exercise veel mooier en uitgebreider dan in de design.md beschreven. Als laatste heb ik geen highscore voor gebruikers toegevoegd.

# Verdediging beslissingen
De reden dat ik geen highscore heb toegevoegd is omdat dit me te veel tijd kostte. Ik heb namelijk der mate veel tijd besteed aan het maken van de schaakpuzzel en vooral het uitvoeren/leesbaar maken  van zetten om het bord te visualiseren dat het me niet gelukt is. Verder vind ik het positief om een instructie te maken voor de app. Als laatste denk ik dat goed en fout meer overzichtelijk is dan totaal en percentage goed in activity_statistics.


