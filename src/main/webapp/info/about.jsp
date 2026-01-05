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

.team-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 24px;
  margin-top: 24px;
}

.team-member {
  text-align: center;
  padding: 24px;
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.05) 0%, rgba(118, 75, 162, 0.05) 100%);
  border-radius: 12px;
  transition: transform 0.3s ease;
}

.team-member:hover {
  transform: translateY(-5px);
}

.team-icon {
  font-size: 64px;
  margin-bottom: 16px;
}

.team-member h3 {
  font-size: 20px;
  color: #333;
  margin-bottom: 8px;
}

.team-member p {
  color: #666;
  font-size: 14px;
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
    <h1>🐆 Giới Thiệu ALOHCMUTE</h1>
    <p style="font-size: 18px; opacity: 0.9;">Mạng xã hội sinh viên - Kết nối và chia sẻ</p>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-info-circle"></i> Về Dự Án</h2>
    <p style="line-height: 1.8; color: #555; font-size: 16px;">
      ALOHCMUTE là một nền tảng mạng xã hội được thiết kế dành riêng cho cộng đồng sinh viên. 
      Dự án được phát triển với mục đích tạo ra một không gian trực tuyến nơi sinh viên có thể 
      kết nối, chia sẻ kiến thức, trao đổi thông tin và xây dựng mối quan hệ.
    </p>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-people"></i> Đội Ngũ Phát Triển</h2>
    <p style="line-height: 1.8; color: #555; font-size: 16px; margin-bottom: 24px;">
      Dự án được thực hiện bởi <strong style="color: #667eea;">Nhóm 3 Con Báo</strong> - 
      một nhóm sinh viên đam mê công nghệ và lập trình web. Chúng tôi đã cùng nhau xây dựng 
      nền tảng này như một phần của môn <strong>Lập Trình Web</strong>, với mong muốn không chỉ 
      hoàn thành yêu cầu học tập mà còn tạo ra một sản phẩm thực tế có giá trị.
    </p>
    
    <div class="team-grid">
      <div class="team-member">
        <div class="team-icon">🐆</div>
        <h3>Thành viên 1</h3>
        <p>Full-stack Developer</p>
        <p style="color: #667eea; font-weight: 600; margin-top: 8px;">Backend & Database</p>
      </div>
      
      <div class="team-member">
        <div class="team-icon">🐆</div>
        <h3>Thành viên 2</h3>
        <p>Frontend Developer</p>
        <p style="color: #667eea; font-weight: 600; margin-top: 8px;">UI/UX Design</p>
      </div>
      
      <div class="team-member">
        <div class="team-icon">🐆</div>
        <h3>Thành viên 3</h3>
        <p>Full-stack Developer</p>
        <p style="color: #667eea; font-weight: 600; margin-top: 8px;">Testing & Deployment</p>
      </div>
    </div>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-bullseye"></i> Mục Đích</h2>
    <ul style="line-height: 2; color: #555; font-size: 16px;">
      <li><strong>Thực hành kỹ năng:</strong> Áp dụng kiến thức lập trình web vào thực tế</li>
      <li><strong>Xây dựng sản phẩm:</strong> Tạo ra một ứng dụng web hoàn chỉnh và có thể sử dụng</li>
      <li><strong>Kết nối sinh viên:</strong> Cung cấp nền tảng để sinh viên giao lưu, học hỏi</li>
      <li><strong>Chia sẻ tri thức:</strong> Tạo môi trường chia sẻ kiến thức và kinh nghiệm</li>
    </ul>
  </div>

  <div class="info-card">
    <h2><i class="bi bi-code-square"></i> Công Nghệ Sử Dụng</h2>
    <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 16px; margin-top: 16px;">
      <div style="padding: 16px; background: #f8f9fa; border-radius: 8px; text-align: center;">
        <strong style="color: #667eea;">Backend</strong>
        <p style="margin-top: 8px; color: #666;">Java, Servlets, JSP</p>
      </div>
      <div style="padding: 16px; background: #f8f9fa; border-radius: 8px; text-align: center;">
        <strong style="color: #667eea;">Database</strong>
        <p style="margin-top: 8px; color: #666;">SQL Server, Hibernate</p>
      </div>
      <div style="padding: 16px; background: #f8f9fa; border-radius: 8px; text-align: center;">
        <strong style="color: #667eea;">Frontend</strong>
        <p style="margin-top: 8px; color: #666;">Bootstrap 5, CSS3</p>
      </div>
      <div style="padding: 16px; background: #f8f9fa; border-radius: 8px; text-align: center;">
        <strong style="color: #667eea;">Server</strong>
        <p style="margin-top: 8px; color: #666;">Apache Tomcat 9</p>
      </div>
    </div>
  </div>

  <div style="text-align: center;">
    <a href="${pageContext.request.contextPath}/user/home" class="back-btn">
      <i class="bi bi-arrow-left"></i> Quay Lại Trang Chủ
    </a>
  </div>
</div>
