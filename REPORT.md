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
