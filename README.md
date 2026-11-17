# spring-boot-demo

## hello-rest

Das Modul `hello-rest` enthält eine CRUD-Demo zur Verwaltung von Personen
(Name und E-Mail). Die Browseroberfläche und die REST-API verwenden eine
In-Memory-H2-Datenbank.

Anwendung starten:

```bash
cd hello-rest
./mvnw spring-boot:run
```

Danach ist die Oberfläche unter <http://localhost:8080/> erreichbar. Die
REST-Endpunkte liegen unter `/api/people`:

| Methode | Pfad | Aktion |
| --- | --- | --- |
| GET | `/api/people` | Personen auflisten |
| GET | `/api/people/{id}` | Person anzeigen |
| POST | `/api/people` | Person anlegen |
| PUT | `/api/people/{id}` | Person ändern |
| DELETE | `/api/people/{id}` | Person löschen |
