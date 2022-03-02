# Währungsrechner
Diese einfache Anwendung stellt einen Währungsrechner dar, der mittels **MVC** unterteilt worden ist.

### Model
Das Model beinhaltet die zugrunde liegende `logische Struktur` von Daten in einer Anwendung und wird vom View dargestellt, die damit verbunden ist.<br/>
Dieses Objektmodell enthält *keine* Informationen zur Benutzeroberfläche oder Steuerung.

### View
Das View ist eine Sammlung von Klassen, welches die ***Elemente in der Benutzeroberfläche darstellen*** (alles, was der Benutzer sieht und interagieren kann).<br/>
Sie ist vor allem für die `Darstellung` der Daten aus dem Model und die `Entgegennahme` von Benutzerinteraktionen aus dem Controller zuständig.

### Controller
Der Controller ***verbindet*** das Modell und die View miteinander und ist für `Kommunikation` zwischen diesen zwei Sachen zuständig.</br>
Er nimmt ***Benutzeraktionen*** entgegen, wertet diese aus und agiert entsprechend.

![](mvc.png)

``Währungskurse entnommen von: [https://www.exchange-rates.org](https://www.exchange-rates.org)``