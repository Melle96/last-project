# Beschrijving app

Deze app heeft als doel gevorderde schakers te laten oefenen met blindschaken. De app maakt gebruik van schaakpuzzels. Hierbij wordt een schaakpuzzel verkregen via de API. Deze schaakpuzzel heeft 1 oplossing met een waarbij de gebruiker een aantal zetten moet doen om de puzzel op te lossen. De app slaat verder per gebruiker op hoeveel tijd deze heeft besteed aan de puzzels. Ook wordt er opgeslagen hoeveel puzzels de gebruiker goed/fout heeft beantwoord.

![blindfoldchess_1](https://user-images.githubusercontent.com/36193067/41974752-18de5630-7a19-11e8-92d9-aa6266cebb10.png)

# Technische vormgeving

Allereerst zal er een korte beschrijving van de structuur van de app gegeven worden. Als de app geopend wordt verschijnt een menu waarin ingelogd kan worden met e-mail en wachtwoord. Indien de gebruiker nog geen account heeft kan deze doorschakelen naar het registreerscherm. Op dat scherm kan dan vervolgens een account aangemaakt worden.

Indien de gebuiker inlogt verschijnt het activity_menu. Dit is een menu waarin de gebuiker kan doorschakelen naar een schaakpuzzel, de statistieken of de instructie. In de instructie staat bescheven hoe de schaakpuzzel ingevuld moet worden door de gebruiker met behulp van notatie. Als de gebuiker doorschakelt naar statistieken ziet deze hoeveel puzzels er correct en fout zijn beantwoord. Ook is er weergeven hoeveel minuten een gebruiker aan het maken van de schaakpuzzels heeft besteed. Een schaakpuzzels weergeeft een schaakpuzzel die uiteindelijk goed of fout beantwoord wordt.

# Uitgebreide uitleg werking app

### activity_main

In deze activity is het de bedoeling voor de gebuiker om in te loggen. De gebuiker kan hier zijn email en bijbehorende wachtwoord invullen.
Indien er dan op sign in geklikt wordt gebeurd er het volgende. Allereerst wordt via de functie logIn het ingevulde email en wachtwoord verkregen. Indien één van de twee velden leeg is, wordt er een toast gemaakt waarin duidelijk wordt gemaakt aan de gebuiker dat deze velden leeg zijn. Als de velden allebei ingevuld zijn maar email en wachtwoord niet matchen wordt een toast gegeven. Als de velden echter wel matchen dan is de log in succesvol en wordt de activity_menu geopend.

Een andere button die de gebuiker kan indrukken is de register button. Indien dit gebeurd, wordt de gebruiker genavigeerd naar de activity_register.

### activity_register
In deze activity is het mogelijk voor een gebruiker zich te registreren. Er kan een email ingevuld worden met wachtwoord en bevestiging van het wachtwoord. Indien de gebruiker dan op register drukt wordt de functie registerNow aangeroepen. In deze functie worden de mail en wachtwoorden verkregen. Indien de wachtwoorden niet matchen wordt dit aangegeven in een toast. Als de email en/of het wachtwoord(1) niet ingevuld is wordt dit aangegeven in een toast. Anders kan er geprobeerd geregistreerd te worden. Als dit niet lukt wordt er opnieuw een toast gegeven. Als er echter wel een succesvolle registratie is dan wordt er allereerst een database voor de gebuiker gemaakt. Via de functie addToDB(user) wordt correct, wrong en time op nul gezet. Als laatste wordt dan de activity_main geopend.

### activity_menu
In deze activity krijgt de gebruiker een overzicht van de activities waarna hij toe kan. Indien de gebuiker klikt op instruction wordt deze genavigeerd naar de activity_instruction. Als de gebruiker klikt op statistics wordt deze genavigeerd naar activity_statistics. Als laatste kan er geklikt worden op puzzle om te navigeren naar activity_chess_exercise.

### activity_instruction
In deze activity wordt een instructie van notatie gegeven aan de hand van een aantal voorbeelden. Er wordt er hierbij vanuit gegaan dat de gebruiker de notatie al kent. Dit is namelijk bij vrijwel alle gevorderde schakers het geval. 

De notatie is een middel om zetten te communiceren zonder het bord te tekenen. De notatie is onder meer verplicht voor amateurs en profs om te gebruiken tijdens toernooien of andere partijen. De notatie is dus voor deze app erg handig om te gebruiken aangezien het blindschaken geen bord kent en het alom bekend is.

### activity_statistics
In deze activity worden de statistieken gepresenteerd van een gebruiker. Wanneer men de activity ingaat wordt onmiddelijk de functie readFromDB() aangeroepen. In deze functie wordt de user verkregen met bijbehorend id. Vervolgens worden onder deze id de waarden van de Score.class verkregen. Vervolgens wordt via de functie setText een aanpassing gedaan van de textviews. Hierbij wordt de tekst gezet op correcte antwoorden, foute antwoorden en tijd besteed aan puzzels in minuten. Deze gegevens zijn  dus verkegen uit de Score.class met behulp van de database van Firebase.

##### Score.class
Deze class heeft strings voor correct, wrong en time. Deze waardes kunnen worden aangepast en verkregen.

### activity_chess_exercise
In deze activity kunnen schaakpuzzels opgelost worden. De activity kent meer dan 1000 regels en verwijst ook nog naar meer dan 400 regels in de ChessExerciseFunctions class. Dit maakt de activity dan ook zeer gecompliceerd. 

Wanneer de activity verschijnt wordt allereerst de functie getPuzzleNow aangeroepen. Wanneer deze wordt aangeroepen wordt allereerst het tekstak waarin de de gebruiker de zetten invoert (notationInText) leeg gemaakt. Er is dan alleen een hinttekst te zien namelijk move. Vervolgens wordt er geindexeerd dat de puzzel nog niet correct en fout is maar dat wel nog kan worden. Vervolgens wordt de starttijd bepaald om te meten hoe lang er over de puzzel wordt gedaan. Hierna wordt de puzzle verkregen via de class GetPuzzle.

Nu de puzzel verkregen is wordt de fen die in de API zit gesplit in kleur en postitie met fenStringSplit. De fen weergeeft namelijk de beginpositie en de beginkleur van de puzzel. fenToString schrijft fen dan vervolgens naar een string van 64 (= aantal schaakvelden) elementen om voor elke vakje te bepalen welk stuk erin moet staan. 

Functie returnBoardInText gebruikt de fen om de beginstelling van het bord in notatie weer te geven voor TextView notationStartPosition. Ook createbord gebruikt de fen maar ook fenToString om het bord te visualiseren in de beginstelling. Verder worden met changeText2 de TextViews displayMoves en displayMoves2 geïnitialiseerd, changeColourPieceClickers verandert de kleur van de stukken namelijk ImageView aa t/m af. Als laatste wordt de functie obtainChessMoves uit ChessExerciseFunctions class aangeroepen om de zetten uit de pgn van de API te scheiden.

Nu de puzzel verkregen is wordt zet de functie initialiseClickers() klikkers voor de gridlayoutClickers verkregen. De gebruiker kan dan op de schaakstukken, coordinaten, = en rokadenotatie klikken om de TextView notationInText aan te passen. Zo komt er in notationText een P te staan indien op pion geklikt wordt. 

Nu de puzzel is verkregen en de gebruiker door middel van klikken zetten kan invullen in notationInText wordt de confirm button belangrijk. Indien de gebruiker op confirm klikt wordt in confirmMove(View view) gecheckt of de door de gebruiker ingevulde zet gelijk is aan het antwoord. Als dit niet zo is wordt met de functie readFromDB de tijd van de puzzel opgeteld in firebase ook wrong gaat één omhoog. Merk op dat per puzzel er maar slechts éénmaal wrong=+1 gerekend kan worden. Vervolgens verandert de imageview result in rood. 

Als de gebruiker een goed antwoord geeft worden door middel van makeChessMove de coordinaten oftewel de stelling van het bord aangepast. Vervolgens wordt makeChessMove2() toegepast. Deze functie check allereerst of de funtie is opgelost en in één keer goed is geweest. Correct wordt via readFromDB +1 (ook time wordt aangepast) en imageview result wordt groen. Ook kan het zijn dat de puzzel eerst fout was maar nu goed dan wordt alleen de time aangepast en imageview result wordt geel. Voor beide gevallen geldt dat de correctPuzzleIndex = 3, hiermee wordt aangegeven dat de puzzel is afgesloten. Indien makeChessMove2() gebeurd er niks mee. Het laatste dat makeChessMove2() kan doen is een tegenzet indien de puzzel niet klaar is.

Wanneer de button hint wordt aangeklikt wordt makeChessMove2() tweemaal aangeroepen. Er wordt een zet voor de gebruiker en een tegenzet gedaan. 

Button new puzzle roept getPuzzleNow() aan.

De laatste button show board laat de gridlayoutBoard verschijnen voor drie seconden. De gridlayoutClickers, notationStartPosition, displayMoves en displayMoves2 verdwijnen voor dezelfde tijdsduur. Dit gebeurd met de functie showBoard() die op zijn beurt disappear, emerge1 en emerge2 aanroept.


##### GetPuzzle.class
In deze class wordt er vanaf https://api.chess.com/pub/puzzle/random een api verkregen. Deze structuur van api ziet er als volgt uit: 

{"title":"An Eye For An Eye","url":"https://www.chess.com/forum/view/daily-puzzles/6-30-2017-an-eye-for-an-eye","publish_time":1498806000,"fen":"6k1/r6p/pp1p2p1/2pP2q1/P1Pb4/4B3/1P1Q2PP/5RK1 w - - 0 1","pgn":"[Date \"????.??.??\"]\r\n[Result \"*\"]\r\n[FEN \"6k1/r6p/pp1p2p1/2pP2q1/P1Pb4/4B3/1P1Q2PP/5RK1 w - - 0 1\"]\r\n\r\n1. Qxd4 cxd4 2. Bxg5\r\n*","image":"https://www.chess.com/dynboard?fen=6k1/r6p/pp1p2p1/2pP2q1/P1Pb4/4B3/1P1Q2PP/5RK1%20w%20-%20-%200%201&size=2"}.

# uitdagingen gedurende ontwikkeling app


