# Proiect Energy System Etapa 2

## Despre

Proiectare Orientata pe Obiecte, Seriile CA, CD
2020-2021

<https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa2>

Student: Chivereanu Radu, 325 CA

## Implementare

### Entitati

- Consumer si Distributor mostenesc clasa Player.  
- InputParser e folosita pentru parsearea inputului.  
- ObjectListFactory: clasa factory care creeaza liste de
- obiecte pe baza inputului.  
- Players: clasa generica container pt obiecte. Le retine si in lista
dar si cu un hashmap pentru acces rapid.  
- Producer simuleaza un producator.  
- OutputWriter se ocupa cu scrierea outputului.  
- Change: clasa care descrie o schimbare lunara.  
- Contract: clasa care descrie un contract.  
- Simulator: clasa care simuleaza logica.  
- Updater: clasa care updateaza obicetele pe baza
noilor informatii.  
- Visa: clasa care se ocupa cu operatiile de platit/primit
capital.  
- Strategy, GreenStrategi, PriceStrategy etc sunt necesare strategiilor.  
- GreenDector clasa utilitara care afla daca o strategie este green.  



### Flow

Contractele sunt tinute intr-un TreeSet dupa pret si dupa id, mentinandu-le astfel ordinea crescatoare dupa pret   
- La inceput distribuitorii isi aleg producatori pe baza strategiei
- In fiecare luna consumatorii primesc salarii, dupa care cei care nu au contract in luna repectiva primesc cel mai bun 
contract ca pret. Se vor plati darile si de catre consumatori si de catre distribuitori, dupa care se vor scoate 
cei falimentati din joc. Dupa ce se iau noile update-uri, se distribuitorii isi realeg producatori
daca este cazul.
- Simularea are nrTurns + 1 pasi pt a lua in considerare si luna de start



### Elemente de design OOP
Abstractizare  
-  Clasa abstracta Player mostenita de Distributor si Consumer.
- Interfata Strategy implementate de 3 alte clase

Incapsulare
-  Toate campurile si unele metode sunt private
        



### Design patterns

- Factory: ObjectListFactory -> construieste liste de obiecte pe baza listelor de json
din input
- Singleton: InputParser
- Strategy: pentru ca distribuitorii au strategii diferite de alegere a distribuitorilor
- Observer: Subiect: Updater, Observatori: Distributor, Producer

### Dificultati intalnite, limitari, probleme

- Players este clasa generica iar retinerea in map are nevoie de o cheie. De aceea
am folosit reflection pentru a apela metoda getId() pe parametrul primit la metoda
de add. Daca pot, folosesc id ca si cheie, daca nu dau mesaj ca pot baga in containerul
Players doar obiecte care au metoda getId 

