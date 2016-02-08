#Otobüs Durağı Simülasyonu Android Uygulaması

<img src="/images/1 activity_main.png" alt="Activity Main" width="auto" height="550">

* Uygulamamız basitçe kendi içinde çalışan bir simülasyondur.

* Başlangıç ekranında kullanıcıdan azami otobüs sayısı ve otobüs durağı sayısı olmak üzere `2 tane` başlangıç parametresi alınmaktadır.

* Bir sonraki ekranda her otobüs için simülasyonu başlattıktan kaç dakika sonra sefere başlayacağını gösteren `[0, 4]` aralığında rastgele bir sayı üretilip kullanıcı bilgilendiriyorum.

* İleri tuşuna basıldığında Duraklar arası mesafeyi gösteren `(DurakSayisi – 1)` tane rastgele sayı üretiyor ve aynı şekilde kullanıcı bilgilendiriyorum. Uygulama içinde tüm otobüslerin hızlarının `sabit ve 50 m/dk ` olduğunu kabul ettim. Bu sebeple duraklar arası mesafeler için sayı üretirken 50'nin katı olacak şekilde sayıları üretiyorum.

* Otobüslerin izleyecekleri rotalar aynıdır. N durak varsa eğer rotanın her zaman `Durak1, Durak2, ... ,DurakN` şeklinde olacağı kabul ediliyor.

* Başla tuşuna basmamızla beraber `MyService` isimli servis başlatılıyor. Servis, butona basıldığı andaki servis saatini alıyor ve her saniye bir dakikaya karşılık gelecek şekilde simulasyon saatini güncelliyorum. Kullanıcı durak listesinden herhangi bir durağa tıklarsa yeni bir aktivite açılıyor. Bu aktivite de aynı servisi kullanarak seçilen durağa gelecek otobüslerin tahmini gelme sürelerini yazan bir listeyi gösteriyor.

* Bu hesaplamayı aşağıdaki kodla yapıyorum.
      ```java
      if (busStartTime[i] + value - count >= 0) {
            kacDakika.put(i, busStartTime[i] + value - count);
      } else {
            kacDakika.remove(i);
      }
      ```
* `kacDakika` değişkenim `HashMap<Integer, Integer>` türünde bir nesne. İlk indis bana hangi otobüs olduğunu, ikinci indis ise otobüsün kaç dakika sonra, seçilen durağa varacağını verecek.

    **busStartTime :** busStartTime dizisi her otobüs için simulasyondan başladıktan kaç dakika sonra sefere başlayacakları bilgisini tutan bir tamsayı dizisidir. <br/>
    **list[position] &nbsp;:** İlk duraktan kalkan bir otobüsün kaç dakika sonra seçilen durağa varacağını tutuyor. <br/>
    **count &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:** Simulasyon başladığından itibaren kaç dakika geçmiş onun bilgisini tutuyor. <br/>

* Otobüse tıklandığında ise aşağıdaki hesaplama ile hangi duraklara uğrayacağını tespit etmiş oluyorum.
       ```java
       if (count - busStartTime[position] <= list[j]) {
            gececegiDuraklar.put(j, "Durak" + (j + 1));
       } else {
            gececegiDuraklar.remove(j);
       }
       ```
       
       **count :** Simulasyon başladığından itibaren kaç dakika geçmiş onun bilgisini tutuyor. <br/>
       **list[i] &nbsp;:** İlk duraktan kalkan bir otobüsün kaç dakika sonra i. indisli durağa varacağını tutuyor. <br/>
       **busStartTime[position] :** Seçilen otobüs için simulasyon başladıktan kaç dakika sonra sefere başlayacağı bilgisini tutan bir tamsayı. <br/>
       
## Kullanılan Veri Yapıları

**Otobüslerin uğrayacağı durakları tutmak için:** `HashMap<Integer, String> gececegiDuraklar` <br/>
**Otobüsler kaç dakika sonra durağa varacaklar bunun için :** `HashMap<Integer, Integer> kacDakika` <br/>
**Duraklar arası mesafeler, Otobüslerin kalkış saatleri vs. için :** `int dizisi şeklinde` <br/>

## Servis ve Intent-filter

* `MyService` sınıfı, `BusActivity`, `BusStopActivity` ve `ServiceDriver` sınıflarıma Broadcast mesajı gönderiyor. Bunun için kullandığım intent-filter şu şekilde :

       ```java
       IntentFilter mainFilter = new IntentFilter("fatihbozik.action.MYSERVICE");
       receiver = new MyBroadcastReceiver();
       registerReceiver(receiver, mainFilter);
       ```
