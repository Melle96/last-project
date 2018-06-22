# dag 4, 7 juni

Tot nu toe heb ik de read.me en design.me gemaakt. Ook ben ik al begonnen met het implementeren van de app. Zo heb ik al een schaakbord gemaakt met behulp van gridlayout. Dit schaabord bestaat uit 64 imageviews met achtergrondkleuren wit en zwart. Ook heb ik al de schaakstukken ingeladen in dwawable. 

![schaakbord](https://user-images.githubusercontent.com/36193067/41087605-696ace3a-6a3d-11e8-8a43-584ad7cff8cb.png)
![schaakbord_code](https://user-images.githubusercontent.com/36193067/41087606-69883ad8-6a3d-11e8-8fcd-8e8862540005.png)

Mijn volgende doel is het inladen van de beginstelling: 3B2k1/pp5p/6q1/1PRp4/4pK2/P7/B4PP1/8 b - - 0 1

# dag 5, 8 juni
Het is me gisteren gelukt om de beginstelling in te laden met de hardcoded string: 3B2k1/pp5p/6q1/1PRp4/4pK2/P7/B4PP1/8.

![img_7943](https://user-images.githubusercontent.com/36193067/41151198-5f1ae040-6b10-11e8-8c19-bbd1cec4d1de.JPG)

Nu is het de taak om de zetten 1. Qxc6 d2 2. Qf6+ Qg7 3. Qxd8+ mooi in te laden. Het is jammer dat het in de korte notatie staat aangezien er nu veel meer zoekwerk nodig is om de plek te vinden waar het stuk staat.

# dag 6, 11 juni
Het is vrijdag gelukt om een zet met een loper te maken. Deze zet kan alleen gedaan worden als er slechts 1 loper op het bord staat. Nu moet het mogelijk gemaakt worden voor alle stukken met meerdere stukken op het bord om zetten te doen. Dit is dan ook mijn doel voor de komende dagen.

# dag 7, 12 juni
Het is maandag gelukt om een stuk te verplaatsen voor een loper, dame, toren en pion als er meerdere van deze stukken op het bord staan. Dit lukt alleen als de notatie met drie letters wordt aangegeven. Echter als de notatie met vier letters wordt aangegeven verplaatst het stuk nog niet aangezien ik hier nog wat code voor moet schrijven. Ook wil ik ervoor zorgen dat het paard en de koning kunnen verplaatsen.

# dag 8, 13 juni
Het is gisteren gelukt om voor alle stukken een zet te doen. Deze code was (is) wat groot daardoor ben ik bezig met de codetekst wat kleiner en overzichtelijker te maken. Verder kunnen zetten slechts gedaan worden als deze zijn weergeven in een notatie van drie letters zoals Ke8 (koning gaat naar e8) of Pe5 (pion gaat naar e5). De notatiezetten zoals Lde5 moeten nog geïmplementeerd worden. Dit is echter een stuk makkelijker aangezien met de d de specifieke positie van de loper bekend is. Deze hoeft dus alleen geselecteerd en verwijderd te worden. De laatste zetten die erin moeten worden gezet zijn: e1=Q (pion promoveerd tot dame), 0-0(-0) (rokade), en eventueel een en passant zet. 

# dag 9, 14 juni
Het is me gisteren gelukt om zetten als Lde5 te doen. Verder ben ik begonnen met het inladen van JSON aangezien ik die gehardcoded had. 
Daar ga ik vandaag ook mee verder.

# dag 10, 15 juni
Het is me gelukt om via de API een puzzel op te vragen en de zetten te kunnen vertalen naar beweging op het schaakbord in de app. Vandaag ga ik nog proberen de zetten als promotie en rokade erin te zetten.

# dag 11, 18 juni
Promotie en rokade staan erin. De enige zetten die er nog niet in staan zijn en passant en een pion die slaand promoveert. Dit kan ik vooralsnog niet erin zetten aangezien ik niet weet hoe chess.com dit noteert in de api. Vandaag ga ik beginnen met het gebruiksvriendelijk maken van de de puzzel. Zo moet de gebruiker zelf zetten kunnen doen en daaruit scores kunnen krijgen.

# dag 12, 19 juni
Gisteren ben ik begonnen met het gebruiksvriendelijk maken van de puzzel. Het is nu mogelijk om de puzzel te laten verdwijnen na een aantal seconden. Ook is het mogelijk om zetten in te typen of in te voeren door te klikken op stukken en coördinaten. Vandaag wil ik gaan werken aan goed of fout beantwooren van de opgave. Hierbij wordt duidelijk wanneer er een fout wordt gemaakt. Dan kan de schaker de opgave opnieuw beantwooden. Verder moet een opgave ook goed gerekend worden als alle zetten goed zijn.

# dag 13, 20 juni
Gisteren heb ik gezorgd dat er ingelogd en geregistreerd kan worden met firebase. Ook is het mogelijk om een knop in te drukken bij schaakpuzzels waardoor het schaakbord even zichtbaar wordt. Als het bord dan na 3 seconden automatisch verdwijnt, verschijnt de oorspronkelijke (begin schaakpuzzel) stand in notatie in beeld. Ook staan de stukken in beeld om aan te klikken waardoor er een notatie kan worden ingegeven. Vandaag ga ik verder met firebase, allereerst wil ik het aantal goede puzzels opslaan per gebruiker, dit kan ik dan later uitbreiden.

# dag 14, 21 juni
Gisteren heb ik geregeld dat het mogelijk is om aantal goede opgaven, foute opgaven en totale tijd op te slaan. Dit moet echter nog wel aangepast kunnen worden doot het uitvoeren van puzzels. Dat is dan ook mijn doel voor vandaag. Verder heb ik gisteren wat gewerkt aan de opmaak van de layout. Het mooi structureren van de code probeer ik morgen te doen. Het is namelijk eerst belangrijk dat de functionaliteiten af zijn.

# dag 15, 22 juni
Ik heb gisteren een tijdje vast gezeten met het aanpassen van de scores in firebase. Dat is nu gelukt maar het heeft me veel tijd gekost waardoor ik niet veel verder ben gekomen. Het is nu wel zo dat de basisfunctionaliteiten af zijn. Hierdoor hoeft er alleen nog maar gespeeld te worden met de layout en de opmaak van de app. Verder is het belangrijk dat de schaakpuzzels mooi worden weergeven. Ook de laatste bugs van de API moeten eruit worden gehaald.

