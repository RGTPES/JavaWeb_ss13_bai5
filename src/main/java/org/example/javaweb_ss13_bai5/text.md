# [Bài tập 5] Trang quản lý Đơn thuốc (Integrated CRUD)

## 1. Kịch bản lỗi khi truy cập danh sách chi tiết thuốc (LAZY) sau khi Session đã đóng

Nếu Prescription có quan hệ với PrescriptionDetail bằng FetchType.LAZY, thì danh sách chi tiết thuốc sẽ chưa được load ngay khi lấy Prescription.

Ví dụ:
- Controller lấy ra danh sách đơn thuốc
- Session Hibernate đã đóng
- Sang Thymeleaf mới gọi prescription.details

Khi đó Hibernate không còn Session để truy vấn tiếp dữ liệu lazy, nên sẽ phát sinh lỗi:

LazyInitializationException

---

## 2. Vì sao lỗi này xảy ra?

Vì:
- LAZY chỉ load dữ liệu khi thực sự truy cập
- Nhưng lúc view truy cập details thì Session đã đóng
- Hibernate không thể lấy thêm dữ liệu từ database

---

## 3. Cách khắc phục

### Cách 1: Load dữ liệu trong Service khi Session còn mở
Truy vấn bằng JOIN FETCH để lấy luôn details trước khi trả về controller.

Ví dụ:
from Prescription p left join fetch p.details where p.id = :id

👉 Đây là cách nên dùng nhất.

---

### Cách 2: Gọi p.getDetails().size() khi Session còn mở
Cách này ép Hibernate load collection trước khi đóng Session.

Ví dụ:
prescription.getDetails().size();

👉 Dùng được nhưng không đẹp bằng JOIN FETCH.

---

### Cách 3: Đổi sang FetchType.EAGER
Hibernate sẽ load details ngay từ đầu.

👉 Không nên lạm dụng vì:
- tốn bộ nhớ
- giảm hiệu năng
- dễ load dư dữ liệu

---

## 4. Kết luận

- Lỗi phát sinh: LazyInitializationException
- Nguyên nhân: truy cập dữ liệu LAZY sau khi Session đã đóng
- Cách khắc phục tốt nhất: dùng HQL JOIN FETCH hoặc khởi tạo dữ liệu khi Session còn mở

---

## 5. Ràng buộc nghiệp vụ nhập liệu

Khi thêm chi tiết thuốc:
- số lượng thuốc không được âm
- nếu quantity < 0 thì phải báo lỗi và không cho lưu

Có thể kiểm tra bằng:
- Validation ở DTO / form
- hoặc kiểm tra thủ công trong Controller / Service

---

## 6. Yêu cầu kỹ thuật chính của bài

- Spring MVC
- Thymeleaf
- Hibernate Native
- Dùng SessionFactory
- Không dùng JPA Repository
- Cấu hình cổng chạy: 8081 trong application.properties
- Chức năng:
    - Xem danh sách đơn thuốc
    - Thêm đơn thuốc mới
    - Tìm kiếm đơn thuốc theo mã bệnh nhân