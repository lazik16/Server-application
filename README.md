Server-application
==================

Aktuálně deploynutá verze serverové aplikace. Bude vždy nahrána sem.

Changelog pro v0.12
- přidány příkazy addUser, getDB(x,y), getUser(x)
- na stránku WebSocket API je přidán popis pro příkazy.
- odebráno náhodné přidávání uživatelů. Nyní server reaguje pouze na výše zmíněné příkazy.
- server nyní využívá port 80.
- do využitých knihoven nyní spadá i gson, pomocí něhož je parsován objekt pro odeslání.
- vzhledem k parsování byl zaveden typ zprávy, ten je parsován spolu s uživatelem, aby příjemci bylo jasné, o jaký objekt se jedná a mohla jej na základě této informace rekonstruovat. Tento konstrukt se nachází ve třídě ObjectJSON.
