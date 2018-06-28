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

## Puzzel
In de puzzel krijgt de schaker een beginstelling te zien. Deze wordt verkegen door middel van https://api.chess.com/pub/puzzle/random. Na een aantal seconden verwijnt het bord. De gebruiker wordt geacht het bord te kennen en de puzzel op te lossen door de juiste zetten in te voeren.

![visualisatie5](https://user-images.githubusercontent.com/36193067/41029638-71d1ca18-697c-11e8-9a43-be971ab85ce8.png)

## (Extra) Schaakvisulatietrainer
Men start bij de beginstelling van het schaakspel. Men selecteert een x aantal zetten. Het bord verdwijnt en er worden x zetten random door de computer uitgevoerd. Het bord zonder stukken verschijnt. Nu is het de taak om de locaties van de stukken aan te tikken. Bij elk goed antwoord worden punten uitgereikt. Bij ieder fout antwoord worden punten in mindering gebracht.

![visualisatie2](https://user-images.githubusercontent.com/36193067/40926531-b6ba7e1a-681c-11e8-80f4-d3749b655e5e.png)

## Firebase
Hierin wordt de data van een gebuiker opgeslagen. De punten die toegekend zijn aan een gebruiker worden hierin opgeslagen. Ook kunnen gebruikers zich via hier registeren en aanmelden.

## API
Schaakpuzzels worden verkregen via de volgende API https://api.chess.com/pub/puzzle/random. Ook de juiste zetten die resulteren in een oplossing van de puzzel worden hierbij verkregen. 

## Moeilijkste gedeelte app
Het omschakelen van de Forsyth–Edwards Notation naar een visualisatie op het bord is lastig. Zo moet 3r3k/pbbr4/1p4Q1/2p4p/7N/P1p3P1/BP3PP1/2R3K1 w KQkq - 0 1 vertaalt worden naar een bord.
Verder is het invoeren van alle schaakregels is een enorme uitdaging. Dit is noodzakelijk om een computer random zetten te laten doen en eventueel een game te creëeren. Vooral het implementeren van regels voor de rokade zijn super moeilijk.

![chesslessons](https://user-images.githubusercontent.com/36193067/40920530-22850f12-680d-11e8-93d6-1454798f9c6b.png)

