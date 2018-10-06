# Tema 3 - Caching System (2017 - 2018)

## Formatul fisierelor de intrare

Prima linie din fisier contine tipul de cache (FIFO, LRU, TIME) si un parametru.
Parametrul reprezinta fie capacitatea, daca este vorba de FIFO / LRU, fie timpul
de expirare al cheilor, daca este vorba de TimeAwareCache.

Restul liniilor reprezinta comenzi de tipul `instructiune argument`, dupa cum
urmeaza: 

  * delay -> adauga un delay de `argument` milisecunde. Este folosita pentru
    testearea `TimeAwareCache`. 
  * get -> afiseaza continutul fisierului `argument`. Daca fisierul nu este in
    cache, el va fi incarcat mai intai, generand un miss si un hit 
  * top_hits / top_misses / top_updates -> afiseaza primele `argument` valori
    pentru hits / misses / updates. Se vor afisa valori si nu chei din cache
    deoarece pot aparea diferente de output in cazul fisierelor cu acelasi numar
    de hits / misses / updates. Foloseste KeyStatsListener.
  * key_hits / key_misses / key_updates -> afiseaza hits / misses / updates
    pentru cheia `argument`. Foloseste KeyStatsListener.
  * total_hits / total_misses / total_updates -> afiseaza hits / misses / updates
    pentru intregul cache. Foloseste StatsListener.

## Rulare checker

Tema va fi testata folosind scriptul `test.sh`. Acesta foloseste la randul lui
Makefile-ul din arhiva.
