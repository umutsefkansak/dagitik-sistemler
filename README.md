# Docker Tabanlı Dağıtık Sistem Mimarisi

Bu proje, Docker teknolojisi kullanılarak oluşturulmuş bir dağıtık sistem mimarisini göstermektedir. Mimari, yük dengeleyici olarak Nginx, replikasyonlu Spring Boot uygulama sunucuları, PostgreSQL veritabanı ve Redis cache sunucusundan oluşmaktadır.

## Mimari Bileşenler


- **Nginx**: Yük dengeleyici olarak çalışarak gelen istekleri Spring Boot uygulama sunucularına dağıtır
- **Spring Boot Uygulamaları**: Yüksek erişilebilirlik için 2 replika olarak çalışan uygulama sunucuları
- **PostgreSQL**: Uygulamanın verilerini saklayan veritabanı sunucusu
- **Redis**: Performansı artırmak için kullanılan önbellek (cache) sunucusu

## Özellikler

- **Yüksek Erişilebilirlik**: Spring Boot uygulamalarının replikasyonu ile sistem kesintisiz hizmet verebilmektedir
- **Failover Mekanizması**: Bir uygulama sunucusu devre dışı kaldığında, Nginx otomatik olarak trafiği çalışan sunucuya yönlendirir
- **Konteynerize Edilmiş Bileşenler**: Tüm sistem bileşenleri Docker konteynerlerinde çalışmaktadır
- **Ölçeklenebilir Yapı**: Docker Compose ile bileşenlerin sayısı kolayca artırılabilir

## Başlangıç

### Gereksinimler

- Docker 20.10 veya üzeri
- Docker Compose 2.0 veya üzeri
- Git

### Kurulum

1. Projeyi klonlayın:
   ```bash
   git clone https://github.com/kullanici/dagitik-sistem-projesi.git
   cd dagitik-sistem-projesi
   ```

2. Docker Compose ile sistemin tüm bileşenlerini başlatın:
   ```bash
   docker-compose up -d
   ```

3. Sistemin çalıştığını doğrulayın:
   ```bash
   docker-compose ps
   ```

## Konfigürasyon Dosyaları

### Docker Compose

```yaml
version: '3.8'
services:
  nginx:
    image: nginx:latest
    container_name: nginx
    ports:
      - "80:80"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf
    depends_on:
      - spring_boot_app1
    networks:
      - app_network
  spring_boot_app1:
    build:
      context: ./spring_boot_app1
      dockerfile: Dockerfile
    deploy:
      replicas: 2
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/mydb
      - SPRING_DATASOURCE_USERNAME=admin
      - SPRING_DATASOURCE_PASSWORD=password
      - SPRING_REDIS_HOST=redis
      - SPRING_REDIS_PORT=6379
      - SERVER_PORT=8080
    networks:
      - app_network
  db:
    image: postgres:latest
    container_name: postgres
    environment:
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: password
      POSTGRES_DB: mydb
    volumes:
      - pg_data:/var/lib/postgresql/data
    networks:
      - app_network
    ports:
      - "5432:5432"
  redis:
    image: redis:latest
    container_name: redis
    networks:
      - app_network
    ports:
      - "6379:6379"
networks:
  app_network:
volumes:
  pg_data:
```

### Nginx Konfigürasyonu

```nginx
events { }
http {
    upstream backend {
        server spring_boot_app1:8080 max_fails=3 fail_timeout=10s;
    }
    server {
        listen 80;
        location / {
            proxy_pass http://backend;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        }
    }
}
```

## Mimari Detayları

### Spring Boot Uygulaması

- İki adet Spring Boot uygulaması replikasyon ile yüksek erişilebilirlik sağlamaktadır
- Her uygulama, PostgreSQL veritabanına ve Redis cache'e bağlanmaktadır
- Uygulamalar Docker konteynerlerinde çalışmaktadır ve özel Dockerfile ile build edilmektedir

### Yük Dengeleme

- Nginx, tüm gelen istekleri backend sunuculara dağıtmaktadır
- `max_fails=3 fail_timeout=30s` parametreleri ile hata tolerans seviyesi ayarlanmıştır
- Bir sunucu 30 saniye içinde 3 kez başarısız olursa, Nginx bu sunucuyu geçici olarak devre dışı bırakır

### Veritabanı

- PostgreSQL veritabanı kalıcı veri depolama için kullanılmaktadır
- Veriler Docker volume aracılığıyla `pg_data` volume'unda saklanmaktadır

### Cache Sistemi

- Redis, performansı artırmak için önbellek (cache) sistemi olarak kullanılmaktadır
- Spring Boot uygulamaları Redis'e bağlanarak sık kullanılan verileri önbellekte tutmaktadır

## Sistem İzleme

Sistemin durumunu aşağıdaki komutlarla izleyebilirsiniz:

```bash
# Tüm konteynerların durumunu görüntüleme
docker-compose ps

# Konteyner loglarını görüntüleme
docker-compose logs -f nginx
docker-compose logs -f spring_boot_app1
docker-compose logs -f db
docker-compose logs -f redis
```
