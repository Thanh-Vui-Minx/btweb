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

.info-card h3 {
  color: #333;
  font-size: 18px;
  font-weight: 600;
  margin: 24px 0 12px 0;
}

.info-card p, .info-card li {
  line-height: 1.8;
  color: #555;
  font-size: 15px;
}

.info-card ul {
  padding-left: 24px;
}

.info-card li {
  margin-bottom: 8px;
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

.highlight-box {
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.08) 0%, rgba(118, 75, 162, 0.08) 100%);
  border-left: 4px solid #667eea;
  padding: 16px 20px;
  border-radius: 8px;
  margin: 16px 0;
}

.date-updated {
  text-align: center;
  color: #999;
  font-size: 14px;
  margin-top: 32px;
  padding-top: 24px;
  border-top: 1px solid #e5e7eb;
}
</style>

<div class="info-container">
  <div class="info-header">
    <h1>🔒 Chính Sách Quyền Riêng Tư</h1>
    <p style="font-size: 18px; opacity: 0.9;">Bảo vệ thông tin cá nhân của bạn</p>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-shield-check"></i> Giới Thiệu</h2>
    <p>
      ALOHCMUTE cam kết bảo vệ quyền riêng tư và thông tin cá nhân của người dùng. 
      Chính sách này mô tả cách chúng tôi thu thập, sử dụng và bảo vệ thông tin của bạn 
      khi bạn sử dụng nền tảng của chúng tôi.
    </p>
    
    <div class="highlight-box">
      <strong style="color: #667eea;">📌 Lưu ý quan trọng:</strong> Bằng việc sử dụng ALOHCMUTE, 
      bạn đồng ý với các điều khoản trong chính sách quyền riêng tư này.
    </div>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-database"></i> Thu Thập Thông Tin</h2>
    
    <h3>1. Thông Tin Bạn Cung Cấp</h3>
    <p>Khi đăng ký và sử dụng dịch vụ, chúng tôi thu thập:</p>
    <ul>
      <li><strong>Thông tin tài khoản:</strong> Tên, email, tên người dùng, mật khẩu</li>
      <li><strong>Thông tin hồ sơ:</strong> Ảnh đại diện, ảnh bìa, tiểu sử cá nhân</li>
      <li><strong>Nội dung:</strong> Bài viết, bình luận, tin nhắn, ảnh/video bạn đăng tải</li>
      <li><strong>Thông tin tương tác:</strong> Lượt thích, theo dõi, chia sẻ</li>
    </ul>

    <h3>2. Thông Tin Tự Động Thu Thập</h3>
    <ul>
      <li><strong>Thông tin thiết bị:</strong> Địa chỉ IP, loại trình duyệt, hệ điều hành</li>
      <li><strong>Thông tin sử dụng:</strong> Thời gian truy cập, trang đã xem, tính năng sử dụng</li>
      <li><strong>Cookie:</strong> Dữ liệu lưu trữ để cải thiện trải nghiệm người dùng</li>
    </ul>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-gear"></i> Sử Dụng Thông Tin</h2>
    <p>Chúng tôi sử dụng thông tin của bạn để:</p>
    <ul>
      <li>Cung cấp và vận hành dịch vụ mạng xã hội</li>
      <li>Cá nhân hóa trải nghiệm người dùng</li>
      <li>Gửi thông báo về hoạt động tài khoản</li>
      <li>Cải thiện và phát triển nền tảng</li>
      <li>Bảo vệ an ninh và phòng chống gian lận</li>
      <li>Tuân thủ các quy định pháp luật</li>
    </ul>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-share"></i> Chia Sẻ Thông Tin</h2>
    
    <h3>Chúng tôi KHÔNG bán thông tin cá nhân của bạn.</h3>
    <p>Thông tin của bạn có thể được chia sẻ trong các trường hợp sau:</p>
    <ul>
      <li><strong>Với người dùng khác:</strong> Thông tin hồ sơ công khai, bài viết, bình luận</li>
      <li><strong>Với nhà cung cấp dịch vụ:</strong> Các đối tác hỗ trợ vận hành hệ thống</li>
      <li><strong>Tuân thủ pháp luật:</strong> Khi có yêu cầu hợp pháp từ cơ quan chức năng</li>
      <li><strong>Bảo vệ quyền lợi:</strong> Để ngăn chặn hành vi vi phạm hoặc gian lận</li>
    </ul>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-lock"></i> Bảo Mật Thông Tin</h2>
    <p>Chúng tôi áp dụng các biện pháp bảo mật để bảo vệ thông tin của bạn:</p>
    <ul>
      <li>Mã hóa dữ liệu nhạy cảm (mật khẩu, thông tin cá nhân)</li>
      <li>Sử dụng giao thức HTTPS để truyền tải dữ liệu an toàn</li>
      <li>Kiểm soát truy cập nghiêm ngặt đối với dữ liệu người dùng</li>
      <li>Sao lưu dữ liệu thường xuyên để phòng ngừa mất mát</li>
      <li>Giám sát hệ thống 24/7 để phát hiện và ngăn chặn xâm nhập</li>
    </ul>
    
    <div class="highlight-box">
      <strong style="color: #667eea;">⚠️ Lưu ý:</strong> Không có hệ thống nào an toàn tuyệt đối 100%. 
      Chúng tôi khuyến nghị bạn sử dụng mật khẩu mạnh và không chia sẻ thông tin đăng nhập.
    </div>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-person-check"></i> Quyền Của Bạn</h2>
    <p>Bạn có các quyền sau đối với thông tin cá nhân của mình:</p>
    <ul>
      <li><strong>Truy cập:</strong> Xem thông tin cá nhân mà chúng tôi lưu trữ</li>
      <li><strong>Chỉnh sửa:</strong> Cập nhật hoặc sửa đổi thông tin không chính xác</li>
      <li><strong>Xóa:</strong> Yêu cầu xóa tài khoản và thông tin liên quan</li>
      <li><strong>Từ chối:</strong> Không đồng ý với một số hoạt động xử lý dữ liệu</li>
      <li><strong>Di chuyển:</strong> Yêu cầu xuất dữ liệu của bạn sang định dạng khác</li>
    </ul>
    
    <p style="margin-top: 16px;">
      Để thực hiện các quyền trên, vui lòng liên hệ với chúng tôi qua email: 
      <a href="mailto:admin@alohcmute.com" style="color: #667eea; font-weight: 600;">admin@alohcmute.com</a>
    </p>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-cookie"></i> Cookie & Công Nghệ Theo Dõi</h2>
    <p>Chúng tôi sử dụng cookie và các công nghệ tương tự để:</p>
    <ul>
      <li>Duy trì trạng thái đăng nhập của bạn</li>
      <li>Ghi nhớ tùy chọn và cài đặt của bạn</li>
      <li>Phân tích cách bạn sử dụng nền tảng</li>
      <li>Cải thiện hiệu suất và trải nghiệm người dùng</li>
    </ul>
    <p>Bạn có thể quản lý cookie thông qua cài đặt trình duyệt của mình.</p>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-people"></i> Quyền Riêng Tư Của Trẻ Em</h2>
    <p>
      ALOHCMUTE không có mục đích thu thập thông tin từ người dùng dưới 16 tuổi. 
      Nếu chúng tôi phát hiện việc thu thập vô tình thông tin từ trẻ em, 
      chúng tôi sẽ xóa thông tin đó ngay lập tức.
    </p>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-arrow-repeat"></i> Thay Đổi Chính Sách</h2>
    <p>
      Chúng tôi có thể cập nhật chính sách quyền riêng tư này theo thời gian. 
      Các thay đổi quan trọng sẽ được thông báo qua email hoặc thông báo trên nền tảng. 
      Việc bạn tiếp tục sử dụng dịch vụ sau khi có thay đổi đồng nghĩa với việc bạn chấp nhận 
      chính sách mới.
    </p>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-envelope"></i> Liên Hệ</h2>
    <p>
      Nếu bạn có bất kỳ câu hỏi nào về chính sách quyền riêng tư này, vui lòng liên hệ:
    </p>
    <ul style="list-style: none; padding-left: 0;">
      <li style="margin: 12px 0;">
        <i class="bi bi-envelope" style="color: #667eea; margin-right: 8px;"></i>
        Email: <a href="mailto:admin@alohcmute.com" style="color: #667eea; font-weight: 600;">admin@alohcmute.com</a>
      </li>
      <li style="margin: 12px 0;">
        <i class="bi bi-telephone" style="color: #667eea; margin-right: 8px;"></i>
        Hotline: <strong>090 000 0000</strong>
      </li>
    </ul>
    
    <div class="date-updated">
      <i class="bi bi-calendar-check"></i> Cập nhật lần cuối: 23/12/2025
    </div>
  </div>

  <div style="text-align: center;">
    <a href="${pageContext.request.contextPath}/user/home" class="back-btn">
      <i class="bi bi-arrow-left"></i> Quay Lại Trang Chủ
    </a>
  </div>
</div>
