# ShiftLogger-Client

Skrivebordsklient for registrering av arbeidstimer og beregning av lønn og overtid. Klienten kommuniserer med [shiftlogger-api](../shiftlogger-api), som kjører i drift på egen server.

> Dette er et hobbyprosjekt laget for eget bruk, ikke for distribusjon til andre brukere.

## Funksjoner

- Innlogging og registrering mot backend-API-et
- Registrere, redigere og slette arbeidsøkter
- Månedsoversikt over registrerte timer
- Automatisk beregning av pauser, overtid og lønn basert på timelønn og overtidsfaktor
- Mørkt og lyst tema
- Minimering til systemstatusfeltet

## Arkitektur

Applikasjonen er lagdelt slik at brukergrensesnittet ikke vet noe om HTTP eller JSON:

```
view (Swing-skjermer)
  ↓
controller
  ↓
service (autentisering, timeregistreringer, beregninger)
  ↓
http (all kommunikasjon med API-et)
```

| Lag | Ansvar |
|-----|--------|
| `view` | Swing-UI med `CardLayout`-navigasjon, tema og systray |
| `controller` | Eneste inngangspunkt fra UI-et |
| `service` | Innlogging, lokal cache av registreringer, lønns- og overtidslogikk |
| `http` | `ApiClient` samler all kommunikasjon med serveren |
| `model` | Immutable `record`-typer som deles mellom lagene |

## Sikkerhet

- All kommunikasjon går over HTTPS
- JWT-tokenet holdes kun i minnet og lagres aldri på disk
- HTTP-klienten er bygget på `java.net.http.HttpClient` og Jackson.
- Brukerens data hentes alltid via det autentiserte API-et. Klienten har ingen direkte tilgang til databasen.

## Teknologier

Java · Swing · FlatLaf · Jackson · Maven

## Bygge og kjøre

Krever Java og Maven.

```bash
# Bygg én kjørbar JAR
mvn package

# Kjør
java -jar target/<filnavn>.jar
```
## Kjente svakheter
- Endring av tema restarter i dag hele applikasjonen
- Timeren lever i view, men burde flyttes til service
- Contract er ikke i bruk
  
## Veien videre

- [ ] Fjerne gammel legacy-kode for lokal SQLite-lagring fra før server-integrasjonen
- [ ] Gjøre endring mellom themes mer robust
- [ ] flytte timeren fra view til service
- [ ] Strukturert logging i stedet for utskrift til konsoll
- [ ] Enhetstester, særlig for lønns- og overtidsberegningen
- [ ] Valgfri "husk meg"-funksjon

## Hva jeg har lært

Prosjektet startet som en ren lokal applikasjon med SQLite og ble senere koblet til et eget backend-API. Overgangen har lært meg verdien av tydelig lagdeling: Fordi brukergrensesnittet bare snakker med kontrolleren, kunne lagringen byttes ut uten å endre skjermene.
