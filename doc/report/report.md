```
SELECT COUNT(*) FROM statuses;
```
| NAME          | COUNT |
| ------------- |------:|
| statuses      | 6     |


```
SELECT COUNT(*) FROM fields_of_study;
```
| NAME            | COUNT |
| --------------- |------:|
| fields_of_study | 328   |


```
SELECT COUNT(*) FROM award_levels;
```
| NAME          | COUNT |
| ------------- |------:|
| award_levels  | 5     |


```
SELECT COUNT(*) FROM award_names;
```
| NAME          | COUNT |
| ------------- |------:|
| award_names   | 87    |


```
SELECT COUNT(*) FROM subjects;
```
| NAME          | COUNT |
| ------------- |------:|
| subjects      | 21    |


```
SELECT COUNT(*) FROM universities;
```
| NAME          | COUNT |
| ------------- |------:|
| universities  | 35    |


```
SELECT COUNT(*) FROM secondary_schools;
```
| NAME              | COUNT |
| ----------------- |------:|
| secondary_schools | 71    |


```
SELECT COUNT(*) FROM fos_at_universities;
```
| NAME                 | COUNT |
| -------------------- |------:|
| fos_at_universities  | 7 000 |


```
SELECT COUNT(*) FROM students;
```
| NAME          | COUNT     |
| ------------- |----------:|
| students      | 1 000 000 |


```
SELECT COUNT(*) FROM graduations_from_ss;
```
| NAME                 | COUNT     |
| -------------------- |----------:|
| graduations_from_ss  | 1 250 000 |


```
SELECT COUNT(*) FROM awards;
```
| NAME          | COUNT   |
| ------------- |--------:|
| awards        | 500 000 |


```
SELECT COUNT(*) FROM graduations;
```
| NAME          | COUNT   |
| ------------- |--------:|
| graduations   | 800 000 |


```
SELECT COUNT(*) FROM registrations;
```
| NAME          | COUNT   |
| ------------- |--------:|
| registrations | 200 000 |


**Koľko prihlášok sa nachádza v jednotlivých stavoch**
```
SELECT s.name, COUNT(r.registration_id)
FROM statuses s
JOIN registrations r ON s.status_id = r.status_id
GROUP BY s.status_id
ORDER BY count DESC;
```
| NAME           | COUNT  |
| -------------- |-------:|
| spracováva sa  | 33 762 |
| zamietnutá     | 33 550 |
| zrušená        | 33 279 |
| čaká na platbu | 33 264 |
| podaná         | 33 160 |
| schválená      | 32 985 |


**Koľko je študijných odborov nachádza na rovnakom počte univerzít**
```
SELECT nt.count, COUNT(nt.count) FROM
	(
	SELECT fos.name, COUNT(fu.field_of_study_id)
	FROM fos_at_universities fu
	JOIN fields_of_study fos ON fu.field_of_study_id = fos.field_of_study_id
	GROUP BY fos.field_of_study_id
	ORDER BY count DESC
	) AS nt
GROUP BY nt.count
ORDER BY nt.count;
```
| NAME          | COUNT |
| ------------- |------:|
| 14            |4      |
| 15            |5      |
| 16            |8      |
| 17            |22     |
| 18            |22     |
| 19            |33     |
| 20            |34     |
| 21            |35     |
| 22            |37     |
| 23            |44     |
| 24            |32     |
| 25            |27     |
| 26            |17     |
| 27            |6      |
| 28            |2      |


**Koľko ocenení bolo udelených na jednotlivých úrovniach**
```
SELECT al.name, COUNT(a.award_id)
FROM award_levels al
JOIN awards a ON al.award_level_id = a.award_level_id
GROUP BY al.award_level_id
ORDER BY count DESC;
```
| NAME                  | COUNT   |
| --------------------- |--------:|
| celoštátne kolo       | 100 317 |
| krajské kolo          |  99 963 |
| európske majstrovstvá |  99 953 |
| štátna súťaž          |  99 922 |
| okresné kolo          |  99 845 |


**Ktorých ocenení bolo udelených koľkým študentom**
```
SELECT an.name, COUNT(a.award_id)
FROM award_names an
JOIN awards a ON an.award_name_id = a.award_name_id
GROUP BY an.award_name_id
ORDER BY count DESC
LIMIT 10;
```
| NAME                                         | COUNT |
| -------------------------------------------- |------:|
| The New School Competition                   | 5 937 |
| Stockholm Innovation Scholarship Competition | 5 926 |
| Short Story Competition                      | 5 925 |
| Fordham Business Challenge                   | 5 896 |
| Inkitt Novel Competition                     | 5 862 |
| Short Story Award                            | 5 854 |
| New Voices Competition                       | 5 849 |
| Bath Short Story Award                       | 5 849 |
| David Nathan Meyerson                        | 5 847 |
| Short Fiction Prize                          | 5 843 |


**Z ktorých 10 predmetov bolo najviac maturít**
```
SELECT s.name, COUNT(g.subject_id)
FROM subjects s
JOIN graduations_from_ss g ON s.subject_id = g.subject_id
GROUP BY s.subject_id
ORDER BY count DESC
LIMIT 10;
```
| NAME                         | COUNT  |
| ---------------------------- |-------:|
| Ruský jazyk                  | 59 922 |
| Logika                       | 59 874 |
| Slovenský jazyk a literatúra | 59 868 |
| Nemecký jazyk                | 59 804 |
| Informatika                  | 59 777 |
| Latinský jazyk               | 59 763 |
| Občianska náuka              | 59 581 |
| Fyzika                       | 59 574 |
| Španielsky jazyk             | 59 557 |
| Dejepis                      | 59 539 |


**Na ktorých 10 univerzitách je najviac študijných odborov**
```
SELECT u.name, COUNT(f.university_id)
FROM universities u
JOIN fos_at_universities f ON f.university_id = u.university_id
GROUP BY u.university_id
ORDER BY count DESC
LIMIT 10;
```
| NAME                                                                                   | COUNT |
| -------------------------------------------------------------------------------------- |------:|
| Univerzita Pavla Jozefa Šafárika v Košiciach                                           | 217   |
| Akadémia médií odborná vysoká škola mediálnej a marketingovej komunikácie v Bratislave | 215   |
| Vysoká škola zdravotníctva a sociálnej práce sv. Alžbety v Bratislave                  | 213   |
| Bratislavská medzinárodná škola liberálnych štúdií                                     | 213   |
| Vysoká škola ekonómie a manažmentu verejnej správy v Bratislave                        | 211   |
| Technická univerzita vo Zvolene                                                        | 210   |
| Vysoká škola múzických umení v Bratislave                                              | 210   |
| Slovenská technická univerzita v Bratislave                                            | 208   |
| Trnavská univerzita v Trnave                                                           | 208   |
| Slovenská poľnohospodárska univerzita v Nitre                                          | 207   |


**Koľko študentom chodilo na 10 najnavštevovanejších stredných škôl**
```
SELECT ss.name, COUNT(s.secondary_school_id)
FROM secondary_schools ss
JOIN students s ON ss.secondary_school_id = s.secondary_school_id
GROUP BY ss.secondary_school_id
ORDER BY count DESC
LIMIT 10;
```
| NAME                                                  | COUNT  |
| ----------------------------------------------------- |-------:|
| Gymnázium Andreja Kmeťa                               | 14 335 |
| Súkromná stredná odborná škola ochrany osôb a majetku | 14 313 |
| Stredná odborná škola techniky a služieb              | 14 298 |
| Stredná odborná škola sklárska Lednické Rovne         | 14 292 |
| Stredná odborná škola drevárska                       | 14 278 |
| Pedagogická a kultúrna akadémia                       | 14 245 |
| Stredná priemyselná škola strojnícka                  | 14 221 |
| Gymnázium Ivana Horvátha                              | 14 219 |
| Súkromná stredná odborná škola SD Jednota             | 14 210 |
| Katolícke gymnázium Štefana Moysesa Banská Bystrica   | 14 206 |


**Na ktorých 10 univerzít je prihlásených najviac študentov**
```
SELECT u.name, COUNT(nt.student_id)
FROM universities u
JOIN
	(
	SELECT DISTINCT r.student_id, f.university_id
	FROM registrations r
	JOIN fos_at_universities f ON r.fos_at_university_id = f.fos_at_university_id
	) AS nt ON u.university_id = nt.university_id
GROUP BY u.university_id
ORDER BY count DESC
LIMIT 10;
```
| NAME                                                                                   | COUNT |
| -------------------------------------------------------------------------------------- |------:|
| Akadémia médií odborná vysoká škola mediálnej a marketingovej komunikácie v Bratislave | 6 132 |
| Technická univerzita vo Zvolene                                                        | 6 042 |
| Vysoká škola bezpečnostného manažérstva v Košiciach                                    | 6 038 |
| Vysoká škola zdravotníctva a sociálnej práce sv. Alžbety v Bratislave                  | 6 035 |
| Univerzita Pavla Jozefa Šafárika v Košiciach                                           | 6 019 |
| Bratislavská medzinárodná škola liberálnych štúdií                                     | 6 005 |
| Vysoká škola ekonómie a manažmentu verejnej správy v Bratislave                        | 5 965 |
| Katolícka univerzita v Ružomberku                                                      | 5 900 |
| Vysoká škola múzických umení v Bratislave                                              | 5 892 |
| Trnavská univerzita v Trnave                                                           | 5 883 |


**Na ktorých 10 študijných odboroch ukončilo štúdium neúspešne najviac študentov**
```
SELECT f.name, COUNT(nt.student_id)
FROM fields_of_study f
JOIN
	(
	SELECT DISTINCT g.student_id, fu.field_of_study_id
	FROM graduations g
	JOIN fos_at_universities fu ON g.fos_at_university_id = fu.fos_at_university_id
	WHERE g.graduated = FALSE
	) AS nt ON f.field_of_study_id = nt.field_of_study_id
GROUP BY f.field_of_study_id
ORDER BY count DESC
LIMIT 10;
```
| NAME                            | COUNT |
| ------------------------------- |------:|
| filozofia                       | 1 612 |
| vnútorné choroby                | 1 599 |
| liečebná pedagogika             | 1 581 |
| energetické stroje a zariadenia | 1 577 |
| divadelné umenie                | 1 571 |
| údržba strojov a zariadení      | 1 557 |
| neurovedy                       | 1 551 |
| ochrana rastlín                 | 1 540 |
| teória vyučovania fyziky        | 1 528 |
| informatika                     | 1 526 |


**Početnosť jednotlivých známok, ktoré boli udelené študentom**
```
SELECT g.mark, COUNT(g.mark)
FROM graduations_from_ss g
GROUP BY g.mark
ORDER BY g.mark;
```
| NAME          | COUNT   |
| ------------- |--------:|
| 1             | 312 802 |
| 2             | 312 272 |
| 3             | 312 379 |
| 4             | 312 547 |


**Najobľúbenejšie krstné mená**
```
SELECT s.name, COUNT(s.student_id)
FROM students s
GROUP BY s.name
ORDER BY count DESC
LIMIT 10;
```
| NAME          | COUNT |
| ------------- |------:|
| Júlia         | 5 137 |
| Pravoslav     | 2 734 |
| Bartolomej    | 2 729 |
| Levoslav      | 2 717 |
| Fedor         | 2 710 |
| Miloš         | 2 708 |
| Bonifác       | 2 696 |
| Arpád         | 2 692 |
| Vavrinec      | 2 689 |
| Mikuláš       | 2 687 |


**Najobľúbenejšia priezviská**
```
SELECT s.surname, COUNT(s.student_id)
FROM students s
GROUP BY s.surname
ORDER BY count DESC
LIMIT 10;
```
| NAME          | COUNT |
| ------------- |------:|
| Lubinová      | 748   |
| Uramová       | 742   |
| Krajčovičová  | 727   |
| Kováčiková    | 725   |
| Novotová      | 720   |
| Sokolová      | 715   |
| Sklenárová    | 714   |
| Húšková       | 713   |
| Filipová      | 713   |
| Cesnaková     | 712   |


**Na akých doménach majú študenti emaily**
```
SELECT SUBSTR(s.email, POSITION('@' in s.email) + 1, LENGTH(s.email)), COUNT(s.student_id)
FROM students s
GROUP BY SUBSTR(s.email, POSITION('@' in s.email) + 1, LENGTH(s.email))
ORDER BY count DESC;
```
| NAME          | COUNT  |
| ------------- |-------:|
| szm.sk        | 91 262 |
| outlook.com   | 91 173 |
| sme.sk        | 91 115 |
| zoznam.sk     | 91 112 |
| gmail.com     | 91 071 |
| azet.sk       | 91 040 |
| pokec.sk      | 90 885 |
| post.sk       | 90 776 |
| pobox.sk      | 90 579 |
| inmail.sk     | 90 548 |
| centrum.sk    | 90 439 |


**YEAR(students.birth_at)**
```
SELECT MIN(EXTRACT(YEAR FROM birth_at)),
MAX(EXTRACT(YEAR FROM birth_at)),
AVG(EXTRACT(YEAR FROM birth_at)),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY EXTRACT(YEAR FROM birth_at))
FROM students;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 1916 | 2016 | 1965 | 1941 | 1966 | 1991 |


**YEAR(graduations_from_ss.graduated_at)**
```
SELECT MIN(EXTRACT(YEAR FROM graduated_at)),
MAX(EXTRACT(YEAR FROM graduated_at)),
AVG(EXTRACT(YEAR FROM graduated_at)),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY EXTRACT(YEAR FROM graduated_at))
FROM graduations_from_ss;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 1916 | 2017 | 1991 | 1978 | 1998 | 2010 |


**graduations_from_ss.mark**
```
SELECT MIN(mark),
MAX(mark),
AVG(mark),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY mark)
FROM graduations_from_ss;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 1    | 4    | 2    | 1    | 2    | 4    |


**YEAR(awards.awarded_at)**
```
SELECT MIN(EXTRACT(YEAR FROM awarded_at)),
MAX(EXTRACT(YEAR FROM awarded_at)),
AVG(EXTRACT(YEAR FROM awarded_at)),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY EXTRACT(YEAR FROM awarded_at))
FROM awards;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 1916 | 2017 | 1991 | 1978 | 1998 | 2010 |


**YEAR(graduations.started_at)**
```
SELECT MIN(EXTRACT(YEAR FROM started_at)),
MAX(EXTRACT(YEAR FROM started_at)),
AVG(EXTRACT(YEAR FROM started_at)),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY EXTRACT(YEAR FROM started_at))
FROM graduations;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 1916 | 2017 | 1982 | 1966 | 1987 | 2003 |


**YEAR(graduations.finished_at)**
```
SELECT MIN(EXTRACT(YEAR FROM finished_at)),
MAX(EXTRACT(YEAR FROM finished_at)),
AVG(EXTRACT(YEAR FROM finished_at)),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY EXTRACT(YEAR FROM finished_at))
FROM graduations;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 1917 | 2017 | 1999 | 1992 | 2005 | 2013 |


**YEAR(registrations.changed_at)**
```
SELECT MIN(EXTRACT(YEAR FROM changed_at)),
MAX(EXTRACT(YEAR FROM changed_at)),
AVG(EXTRACT(YEAR FROM changed_at)),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY EXTRACT(YEAR FROM changed_at))
FROM registrations;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 1916 | 2017 | 1991 | 1978 | 1998 | 2010 |


**awards.award_level_id**
```
SELECT MIN(award_level_id), MAX(award_level_id), AVG(award_level_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY award_level_id)
FROM awards;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 1 | 5 | 3 | 2 | 3 | 4 |


**awards.award_name_id**
```
SELECT MIN(award_name_id), MAX(award_name_id), AVG(award_name_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY award_name_id)
FROM awards;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 1 | 87 | 44 | 22 | 44 | 66 |


**awards.student_id**
```
SELECT MIN(student_id), MAX(student_id), AVG(student_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY student_id)
FROM awards;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 3 | 999 998 | 500 081 | 250 153 | 500 414 | 750 432 |


**graduations_from_ss.student_id**
```
SELECT MIN(student_id), MAX(student_id), AVG(student_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY student_id)
FROM graduations_from_ss;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 1 | 1 000 000 | 499 777 | 249 392 | 499 466 | 749 743 |


**graduations_from_ss.subject_id**
```
SELECT MIN(subject_id), MAX(subject_id), AVG(subject_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY subject_id)
FROM graduations_from_ss;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 1 | 21 | 11 | 6 | 11 | 16 |


**students.secondary_school_id**
```
SELECT MIN(secondary_school_id), MAX(secondary_school_id), AVG(secondary_school_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY secondary_school_id)
FROM students;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 1 | 71 | 35 | 18 | 36 | 54 |


**graduations.student_id**
```
SELECT MIN(student_id), MAX(student_id), AVG(student_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY student_id)
FROM graduations;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 3 | 1 000 000 | 499 560 | 249 230 | 499 275 | 749 753 |


**graduations.fos_at_university_id**
```
SELECT MIN(fos_at_university_id), MAX(fos_at_university_id), AVG(fos_at_university_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY fos_at_university_id)
FROM graduations;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 1 | 7 000 | 3 499 | 1 748 | 3 498 | 5 249 |


**registrations.student_id**
```
SELECT MIN(student_id), MAX(student_id), AVG(student_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY student_id)
FROM registrations;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 2 | 999 995 | 500 464 | 250 417 | 501 859 | 750 079 |


**registrations.fos_at_university_id**
```

SELECT MIN(fos_at_university_id), MAX(fos_at_university_id), AVG(fos_at_university_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY fos_at_university_id)
FROM registrations;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 1 | 7 000 | 3 503 | 1 753 | 3 510 | 5 258 |


**registrations.status_id**
```
SELECT MIN(status_id), MAX(status_id), AVG(status_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY status_id)
FROM registrations;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 1 | 6 | 3 | 2 | 3 | 5  |


**fos_at_universities.university_id**
```
SELECT MIN(university_id), MAX(university_id), AVG(university_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY university_id)
FROM fos_at_universities;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 1 | 35 | 17 | 9 | 18 | 27 |


**fos_at_universities.field_of_study_id**
```
SELECT MIN(field_of_study_id), MAX(field_of_study_id), AVG(field_of_study_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY field_of_study_id)
FROM fos_at_universities;
```
| MAX  | MIN  | AVG  | Q1   | Q2   | Q3   |
| ----:| ----:| ----:| ----:| ----:| ----:|
| 1 | 328 | 165 | 84 | 166 | 247 |
