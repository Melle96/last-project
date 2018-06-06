# Last-project
Blindschaaktrainingsapp

# 1 zin
Deze heeft als doel een schaker beter te leren blindschaken

# Probleem
Er zijn miljoenen mensen die weleens een schaakspelletje hebben gespeeld. Dagelijks spelen er tienduizenden mensen online een partij via 
schaakapps of websites. Het doel hierbij is net als in ieder ander spel winnen.
Men vergroot de kans om te winnen door beter te worden. Maar wat is nu eigenlijk de beste manier om beter te worden. Een feit is dat visualisatie een belangrijke rol speelt in het schaakspel. Een manier om beter te leren visualiseren is het oefenen met blindschaak.

# De app
Deze app heeft als voornaamste doel om schakers te leren/ beter te maken in blindschaak door middel van visualisatie spelletjes.

## beginscherm app
In dit scherm kan men zich aanmelden of registreren. Dit aanmelden/registreren gebeurt via de database van Firebase.

## Registratiescherm
Indien men kiest voor registreren dan gaat men door naar het registratiescherm. Hier kan iemand zijn email en wachtwoord met wachtwoord confirmatie invullen.

## Beginscherm
Als de persoon is aangemeld komt men op het beginscherm. Op het beginscherm kan men kiezen voor twee opties. Allereerst is er een optie om te kiezen voor een geschiedenis. Daarnaast is er een scherm waarmee kan kiezen voor schaakpuzzels.

## Rating
In de ratinggeschiedenis wordt weergeven hoeveel puzzels een persoon bepaalde puzzels heeft opgelost. Ook kan men hier vinden hoe lang de schaker in totaal heeft gespendeerd om puzzels op te lossen. Hoeveel hij er goed had en hoeveel fout. 

## Schaakpuzzel
Er wordt hier een notatie neergezet van hoe het bord staat. Ook kan men kiezen voor een verschijning van de bordstelling van x aantal seconden. Het bord verschijnt vervolgens een x aantal seconden. Vervolgens kan men de zetten invoeren voor wat de oplossing moet zijn. Deze invoering kan worden getypt of worden geklikt. Er verschijnen namelijk stukken, letters en cijfers om de notatie te bepalen. Als er een zet is ingevoerd moet men deze bevestigen. Als hij fout is wordt dit aangegeven en kan men opnieuw proberen of een andere puzzel kiezen. Indien de zet goed is, doet (de API, dit is voorgeprogrammeerd) een tegenzet. Nu kan er opnieuw een zet door de gebruiker worden gevoerd. Dit herhaalt zich tot de hele oplossing is gegeven. Dat kost meestal iets van 3 zetten. Indien goed dan kan men kiezen voor nog een opgave of terug te gaan naar het beginscherm.

In de schaakpuzzel is de gehele tijd een klok aanwezig. Op die manier weet de gebuiker precies hoe lang deze over de opgave doet.

![visualisatie5](https://user-images.githubusercontent.com/36193067/41029638-71d1ca18-697c-11e8-9a43-be971ab85ce8.png)

# API
De API die wordt gebruikt is van chess.com en creëert een random schaakpuzzel. De link die daarbij hoort is als volgt:  https://api.chess.com/pub/puzzle/random . Deze link weergeeft de volgende JSON informatie: 

{"title":"Queen Domination","comments":"","url":"https://www.chess.com/forum/view/daily-puzzles/12272009---queen-domination","publish_time":1261900800,"fen":"3B2k1/pp5p/6q1/1PRp4/4pK2/P7/B4PP1/8 b - - 0 1","pgn":"[Date \"????.??.??\"]\r\n[Result \"*\"]\r\n[FEN \"3B2k1/pp5p/6q1/1PRp4/4pK2/P7/B4PP1/8 b - - 0 1\"]\r\n\r\n1...Qd6+ 2. Kf5 Qxc5\r\n*","image":"https://www.chess.com/dynboard?fen=3B2k1/pp5p/6q1/1PRp4/4pK2/P7/B4PP1/8%20b%20-%20-%200%201&size=2"}

In deze informatie weergeeft fen (Forsyth-Edwards Notation) de beginstelling van de puzzel. Vervolgens geeft 1...Qd6+ 2. Kf5 Qxc5 de oplossing van de puzzel. Door deze informatie goed te gebruiken en te vertalen kan een bordvisualisatie van de beginstelling worden gemaakt. Ook de notatie moet goed worden vertaald zodat de gebruiker alleen maar hoeft te klikken op een stuk, cijfer,letter en niet hoeft te typen om een juist antwoord te geven.

# Firebase
Firebase wordt gebuikt om de gegevens van de gebruikers op te slaan. Hierbij wordt gedoeld op de totale puzzeloplostijd, aantal puzzels en percentage goed/fout. 

# Functies
-fen omzetten naar beginstelling

-correcte zetten vertalen voor gebruiker

-firebase: toevoegen, aanpassen, registreren, aanmelden

![chesslessons](https://user-images.githubusercontent.com/36193067/40920530-22850f12-680d-11e8-93d6-1454798f9c6b.png)

