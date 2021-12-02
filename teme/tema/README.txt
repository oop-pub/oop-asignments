Poenaru Octavian 323CD tema 1 POO


Pentru Favorite am luat pe rand cele 3 posibilitati : ca filmul sa nu fie vazut, sa fi fost deja vazut sau cand acesta a fost adaugat la favorite
Pentru primele 2 variante, va afisa un mesaj de eroare
Pentru Rating am facut ceva asemanator cu favorite , dar pentru asta am creat listele users si serial
Pentru a marca un serial ca fiind vazut , am incrementat si dupa am verificat daca acesta a fost vazut.
Pentru view am creat o clasa viewhelp care mapeaza titlul daca aceasta nu exista
Pentru ActorQuery am creat functiile average si prize . Pentru implementarea acestora a fost nevoie de o functiile auxiliare HelperSort care are rolul de a sorta hashmap-ul dupa valoare iar apoi dupa nume , in mod alfabetic ,fie in mod crescator , fie descrescator si de inca o functie Sort. Pentru average se adauga, pentru fiecarea actor ratingurile atat pentru filme
cat si pentru seriale in care acesta a jucat , folosind un hashmap creat in actorinputdata.La final sortez dupa media si numele actorilor . M-am folosit de unmodificable list care returneaza o vizualizare nemodificabila a listei specificate, unde operatiile de interogare din lista returnata „citesc” la lista specificata și incercarile de a modifica lista returnată, fie directă, fie prin iteratorul acesteia.
Pentru prize gasesc toate award-urile si le adaug intr-un map iar apoi le sortez folosind o variabila care, in caz ca unul din awarduri nu se gasesc se opreste cautarea .
Pentru Queryul de filme m-am folosit de o functie care imi returneaza o lista de filme din anul sau genul dat iar la final va afisa un mesaj. Aici m-am folosit tot de functiile HelperSort si Sort
Functia sort a fost facuta cu comparator care returneaza valoarea 0 daca d1 este numeric egal cu d2, o valoare mai mica decat 0 daca d1 este numeric mai mic decât d2 si o valoare mai mare decat 0 daca d1 este numeric mai mare decât d2. Semnul valorii intregi returnate este acelasi cu cel al intregului care va fi returnat de apel
Functia HelperSort a fost facuta cu un iterator . Returneaza o matrice care contine toate elementele din acest set. Dacă acest set ofera "garantii" cu privire la ordinea in care elementele sale sunt returnate de iteratorul sau, aceasta metodă ar trebui sa returneze elementele in aceeasi ordine
FilterMovie a fost facut cu un parseint care returneaza intregul reprezentat de argumentul "sir" iar apoi am folosit collect pentru a acumula elementele stream-ului intr-o lista care va fi returnata la final