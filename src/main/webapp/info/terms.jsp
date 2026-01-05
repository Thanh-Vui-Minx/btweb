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

.warning-box {
  background: rgba(239, 68, 68, 0.08);
  border-left: 4px solid #ef4444;
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
    <h1>📋 Điều Khoản Sử Dụng</h1>
    <p style="font-size: 18px; opacity: 0.9;">Quy định và trách nhiệm khi sử dụng ALOHCMUTE</p>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-file-text"></i> Chấp Nhận Điều Khoản</h2>
    <p>
      Bằng việc truy cập và sử dụng ALOHCMUTE, bạn đồng ý tuân thủ và bị ràng buộc bởi 
      các điều khoản và điều kiện sử dụng dưới đây. Nếu bạn không đồng ý với bất kỳ phần nào 
      của các điều khoản này, vui lòng không sử dụng dịch vụ của chúng tôi.
    </p>
    
    <div class="highlight-box">
      <strong style="color: #667eea;">📌 Quan trọng:</strong> Vui lòng đọc kỹ các điều khoản này 
      trước khi sử dụng dịch vụ. Việc tiếp tục sử dụng đồng nghĩa với việc bạn chấp nhận các điều khoản.
    </div>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-person-plus"></i> Đăng Ký Tài Khoản</h2>
    
    <h3>1. Điều Kiện Đăng Ký</h3>
    <ul>
      <li>Bạn phải từ đủ 16 tuổi trở lên để đăng ký tài khoản</li>
      <li>Cung cấp thông tin chính xác, đầy đủ và cập nhật</li>
      <li>Bạn chịu trách nhiệm bảo mật thông tin đăng nhập của mình</li>
      <li>Một người chỉ được tạo một tài khoản duy nhất</li>
      <li>Không được mạo danh hoặc sử dụng thông tin của người khác</li>
    </ul>

    <h3>2. Bảo Mật Tài Khoản</h3>
    <ul>
      <li>Bạn chịu trách nhiệm cho tất cả hoạt động diễn ra dưới tài khoản của mình</li>
      <li>Thông báo ngay cho chúng tôi nếu phát hiện truy cập trái phép</li>
      <li>Không chia sẻ mật khẩu hoặc thông tin đăng nhập với bất kỳ ai</li>
    </ul>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-shield-exclamation"></i> Quy Định Nội Dung</h2>
    
    <h3>Nội Dung Được Phép</h3>
    <ul>
      <li>Chia sẻ kiến thức, kinh nghiệm học tập và nghề nghiệp</li>
      <li>Đăng tải ảnh, video có nội dung phù hợp</li>
      <li>Tương tác, bình luận một cách lịch sự và tôn trọng</li>
      <li>Kết nối và xây dựng mối quan hệ tích cực</li>
    </ul>

    <h3>Nội Dung Bị Cấm</h3>
    <div class="warning-box">
      <strong style="color: #ef4444;">⚠️ Nghiêm cấm các nội dung sau:</strong>
    </div>
    <ul>
      <li><strong>Bạo lực:</strong> Nội dung kích động bạo lực, thù hận hoặc phân biệt đối xử</li>
      <li><strong>Khiêu dâm:</strong> Hình ảnh, video hoặc văn bản khiêu dâm, đồi trụy</li>
      <li><strong>Spam:</strong> Quảng cáo rác, lừa đảo, tin giả</li>
      <li><strong>Vi phạm bản quyền:</strong> Sử dụng nội dung không có quyền sở hữu</li>
      <li><strong>Thông tin cá nhân:</strong> Chia sẻ thông tin riêng tư của người khác không được phép</li>
      <li><strong>Gian lận:</strong> Các hành vi lừa đảo, chiếm đoạt tài khoản</li>
    </ul>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-person-x"></i> Hành Vi Bị Cấm</h2>
    <p>Người dùng KHÔNG được phép:</p>
    <ul>
      <li>Quấy rối, bắt nạt hoặc đe dọa người dùng khác</li>
      <li>Mạo danh cá nhân, tổ chức hoặc thực thể khác</li>
      <li>Sử dụng bot, script hoặc công cụ tự động hóa không được phép</li>
      <li>Can thiệp vào hoạt động bình thường của hệ thống</li>
      <li>Cố gắng truy cập trái phép vào dữ liệu hoặc tài khoản</li>
      <li>Thu thập thông tin người dùng cho mục đích thương mại</li>
      <li>Tạo nhiều tài khoản giả để spam hoặc lạm dụng hệ thống</li>
    </ul>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-file-earmark-text"></i> Quyền Sở Hữu Nội Dung</h2>
    
    <h3>1. Nội Dung Của Bạn</h3>
    <ul>
      <li>Bạn vẫn giữ quyền sở hữu đối với nội dung mình đăng tải</li>
      <li>Bạn cấp cho ALOHCMUTE quyền sử dụng, hiển thị và phân phối nội dung của bạn trên nền tảng</li>
      <li>Bạn chịu trách nhiệm về tính hợp pháp của nội dung mình đăng tải</li>
    </ul>

    <h3>2. Nội Dung Của ALOHCMUTE</h3>
    <ul>
      <li>Tất cả logo, thiết kế, mã nguồn thuộc quyền sở hữu của ALOHCMUTE</li>
      <li>Không được sao chép, sửa đổi hoặc phân phối mà không có sự cho phép</li>
    </ul>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-exclamation-triangle"></i> Vi Phạm & Xử Lý</h2>
    
    <h3>Hành Động Khi Vi Phạm</h3>
    <p>Tùy vào mức độ vi phạm, chúng tôi có thể:</p>
    <ul>
      <li><strong>Cảnh báo:</strong> Gửi thông báo nhắc nhở về vi phạm</li>
      <li><strong>Xóa nội dung:</strong> Gỡ bỏ nội dung vi phạm</li>
      <li><strong>Tạm khóa:</strong> Đình chỉ tài khoản trong một khoảng thời gian</li>
      <li><strong>Khóa vĩnh viễn:</strong> Xóa tài khoản và cấm truy cập vĩnh viễn</li>
      <li><strong>Báo cáo cơ quan chức năng:</strong> Đối với các vi phạm nghiêm trọng</li>
    </ul>

    <div class="warning-box">
      <strong style="color: #ef4444;">⚠️ Lưu ý:</strong> Chúng tôi có toàn quyền quyết định 
      về việc vi phạm và biện pháp xử lý mà không cần thông báo trước.
    </div>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-shield-check"></i> Miễn Trừ Trách Nhiệm</h2>
    <ul>
      <li>ALOHCMUTE được cung cấp "nguyên trạng" không có bảo hành nào</li>
      <li>Chúng tôi không chịu trách nhiệm về nội dung do người dùng tạo ra</li>
      <li>Không đảm bảo dịch vụ luôn khả dụng 100% không gián đoạn</li>
      <li>Không chịu trách nhiệm về thiệt hại phát sinh từ việc sử dụng dịch vụ</li>
      <li>Người dùng tự chịu trách nhiệm về các tương tác và giao dịch với người dùng khác</li>
    </ul>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-power"></i> Chấm Dứt Dịch Vụ</h2>
    <p>Bạn có thể:</p>
    <ul>
      <li>Ngừng sử dụng dịch vụ bất cứ lúc nào</li>
      <li>Yêu cầu xóa tài khoản thông qua email hoặc hotline</li>
    </ul>
    
    <p style="margin-top: 16px;">Chúng tôi có quyền:</p>
    <ul>
      <li>Tạm ngừng hoặc chấm dứt dịch vụ bất cứ lúc nào</li>
      <li>Đình chỉ hoặc xóa tài khoản vi phạm điều khoản</li>
      <li>Thay đổi, bổ sung hoặc ngừng cung cấp tính năng</li>
    </ul>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-arrow-repeat"></i> Thay Đổi Điều Khoản</h2>
    <p>
      ALOHCMUTE có quyền sửa đổi các điều khoản sử dụng này bất kỳ lúc nào. 
      Các thay đổi sẽ có hiệu lực ngay khi được đăng tải. Chúng tôi sẽ thông báo 
      các thay đổi quan trọng qua email hoặc thông báo trên nền tảng.
    </p>
    
    <div class="highlight-box">
      <strong style="color: #667eea;">💡 Khuyến nghị:</strong> Kiểm tra điều khoản định kỳ 
      để cập nhật các thay đổi mới nhất.
    </div>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-scale"></i> Luật Áp Dụng</h2>
    <p>
      Các điều khoản này được điều chỉnh bởi pháp luật Việt Nam. 
      Mọi tranh chấp phát sinh sẽ được giải quyết tại tòa án có thẩm quyền tại TP. Hồ Chí Minh.
    </p>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-envelope"></i> Liên Hệ</h2>
    <p>
      Nếu bạn có câu hỏi về các điều khoản sử dụng này, vui lòng liên hệ:
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
