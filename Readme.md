# 🚀 Dağıtık Sistem Mimarisi (Docker, Nginx, Spring Boot, PostgreSQL, Redis)

Bu proje, **Docker teknolojisi** kullanarak oluşturulmuş **yüksek erişilebilirlik** sağlayan bir **dağıtık sistem mimarisidir**.  
Mimaride **Nginx yük dengeleyici, Spring Boot uygulamaları, PostgreSQL veritabanı ve Redis cache sunucusu** bulunmaktadır.

## 📌 Mimari Bileşenler  

- **Nginx:** Gelen istekleri Spring Boot uygulamalarına yönlendiren yük dengeleyici.  
- **Spring Boot Uygulamaları:** Replikasyonlu (2 adet) çalışarak yüksek erişilebilirlik sağlar.  
- **PostgreSQL:** Veritabanı servisi olarak kullanılır.  
- **Redis:** Uygulamanın performansını artırmak için cache mekanizması sağlar.  

---

## 🛠 Kurulum & Çalıştırma  

### **1️⃣ Gerekli Bağımlılıklar**  
- Docker  
- Docker Compose  

### **2️⃣ Projeyi Klonlayın**  
```bash
git clone [https://github.com/kullanici-adi/proje-adi.git](https://github.com/umutsefkansak/dagitik-sistemler.git)
cd proje-adi
