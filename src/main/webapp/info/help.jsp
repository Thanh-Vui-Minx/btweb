<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
.info-container {
  max-width: 900px;
  margin: 40px auto;
  padding: 20px;
}

.info-header {
  text-align: center;
  margin-bottom: 40px;
  padding: 40px 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 20px;
  color: white;
}

.info-header h1 {
  font-size: 42px;
  font-weight: 700;
  margin-bottom: 10px;
}

.info-card {
  background: white;
  border-radius: 16px;
  padding: 32px;
  margin-bottom: 24px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.info-card h2 {
  color: #667eea;
  font-size: 24px;
  font-weight: 600;
  margin-bottom: 16px;
  display: flex;
  align-items: center;
  gap: 12px;
}

.contact-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px;
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.05) 0%, rgba(118, 75, 162, 0.05) 100%);
  border-radius: 12px;
  margin-bottom: 16px;
  transition: all 0.3s ease;
}

.contact-item:hover {
  transform: translateX(5px);
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%);
}

.contact-icon {
  font-size: 32px;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-radius: 12px;
}

.contact-info {
  flex: 1;
}

.contact-info h3 {
  font-size: 18px;
  color: #333;
  margin-bottom: 4px;
}

.contact-info p {
  color: #666;
  font-size: 15px;
  margin: 0;
}

.contact-info a {
  color: #667eea;
  text-decoration: none;
  font-weight: 600;
}

.contact-info a:hover {
  text-decoration: underline;
}

.faq-item {
  border-left: 4px solid #667eea;
  padding: 16px;
  margin-bottom: 16px;
  background: #f8f9fa;
  border-radius: 8px;
}

.faq-item h4 {
  color: #333;
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 8px;
}

.faq-item p {
  color: #666;
  line-height: 1.6;
  margin: 0;
}

.back-btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 12px 24px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  text-decoration: none;
  border-radius: 10px;
  font-weight: 600;
  transition: all 0.3s ease;
  margin-top: 24px;
}

.back-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
  color: white;
}
</style>

<div class="info-container">
  <div class="info-header">
    <h1>💬 Trợ Giúp & Liên Hệ</h1>
    <p style="font-size: 18px; opacity: 0.9;">Chúng tôi luôn sẵn sàng hỗ trợ bạn</p>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-envelope"></i> Thông Tin Liên Hệ</h2>
    <p style="color: #666; margin-bottom: 24px;">
      Nếu bạn có bất kỳ câu hỏi, góp ý hoặc cần hỗ trợ, vui lòng liên hệ với chúng tôi qua các kênh sau:
    </p>

    <div class="contact-item">
      <div class="contact-icon">
        <i class="bi bi-envelope-fill"></i>
      </div>
      <div class="contact-info">
        <h3>Email</h3>
        <p>Liên hệ qua email: <a href="mailto:admin@alohcmute.com">admin@alohcmute.com</a></p>
        <p style="font-size: 13px; color: #999; margin-top: 4px;">Thời gian phản hồi: 24-48 giờ</p>
      </div>
    </div>

    <div class="contact-item">
      <div class="contact-icon">
        <i class="bi bi-telephone-fill"></i>
      </div>
      <div class="contact-info">
        <h3>Điện Thoại</h3>
        <p>Hotline hỗ trợ: <a href="tel:0900000000">090 000 0000</a></p>
        <p style="font-size: 13px; color: #999; margin-top: 4px;">Thứ 2 - Thứ 6: 8:00 - 17:00</p>
      </div>
    </div>

    <div class="contact-item">
      <div class="contact-icon">
        <i class="bi bi-geo-alt-fill"></i>
      </div>
      <div class="contact-info">
        <h3>Địa Chỉ</h3>
        <p>Trường Đại học Sư phạm Kỹ thuật TP.HCM</p>
        <p style="font-size: 13px; color: #999; margin-top: 4px;">1 Võ Văn Ngân, Thủ Đức, TP.HCM</p>
      </div>
    </div>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-question-circle"></i> Câu Hỏi Thường Gặp</h2>
    
    <div class="faq-item">
      <h4>❓ Làm sao để đăng ký tài khoản?</h4>
      <p>Nhấn vào nút "Đăng Ký" ở góc trên bên phải, điền đầy đủ thông tin và xác nhận email của bạn.</p>
    </div>

    <div class="faq-item">
      <h4>❓ Tôi quên mật khẩu, phải làm sao?</h4>
      <p>Nhấn vào "Quên mật khẩu" ở trang đăng nhập, nhập email của bạn và làm theo hướng dẫn được gửi qua email.</p>
    </div>

    <div class="faq-item">
      <h4>❓ Làm sao để thay đổi thông tin cá nhân?</h4>
      <p>Vào trang "Hồ Sơ Của Tôi", nhấn nút "Chỉnh Sửa" và cập nhật thông tin mới.</p>
    </div>

    <div class="faq-item">
      <h4>❓ Tôi có thể báo cáo nội dung vi phạm không?</h4>
      <p>Có, bạn có thể nhấn vào nút "Báo Cáo" ở mỗi bài viết hoặc bình luận để thông báo cho quản trị viên.</p>
    </div>

    <div class="faq-item">
      <h4>❓ Làm sao để xóa tài khoản?</h4>
      <p>Vui lòng liên hệ với chúng tôi qua email hoặc điện thoại để được hỗ trợ xóa tài khoản.</p>
    </div>
  </div>

  <div class="info-card" style="background: linear-gradient(135deg, rgba(102, 126, 234, 0.05) 0%, rgba(118, 75, 162, 0.05) 100%); border: 2px solid #667eea;">
    <h2><i class="bi bi-clock-history"></i> Giờ Làm Việc</h2>
    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 16px; margin-top: 16px;">
      <div>
        <strong style="color: #667eea;">Thứ 2 - Thứ 6:</strong>
        <p style="margin: 8px 0 0 0; color: #666;">8:00 - 17:00</p>
      </div>
      <div>
        <strong style="color: #667eea;">Thứ 7 - Chủ Nhật:</strong>
        <p style="margin: 8px 0 0 0; color: #666;">Đóng cửa</p>
      </div>
    </div>
  </div>

  <div style="text-align: center;">
    <a href="${pageContext.request.contextPath}/user/home" class="back-btn">
      <i class="bi bi-arrow-left"></i> Quay Lại Trang Chủ
    </a>
  </div>
</div>
