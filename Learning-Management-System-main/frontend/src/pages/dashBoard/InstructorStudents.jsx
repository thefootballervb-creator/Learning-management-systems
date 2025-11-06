import React, { useEffect, useState } from "react";
import { Table, Avatar, Tag, Card, message } from "antd";
import { UserOutlined } from "@ant-design/icons";
import { instructorService } from "../../api/instructor.service";

function InstructorStudents() {
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchStudents();
  }, []);

  const fetchStudents = async () => {
    setLoading(true);
    try {
      const result = await instructorService.getMyStudents();
      if (result.success) {
        setStudents(result.data);
      } else {
        message.error(result.error || "Failed to fetch students");
      }
    } catch (error) {
      message.error("Error fetching students");
    } finally {
      setLoading(false);
    }
  };

  const columns = [
    {
      title: "Avatar",
      dataIndex: "profileImage",
      key: "avatar",
      width: 80,
      render: (profileImage, record) => (
        <Avatar
          size={40}
          src={profileImage ? `data:image/jpeg;base64,${profileImage}` : null}
          icon={<UserOutlined />}
        />
      ),
    },
    {
      title: "Username",
      dataIndex: "username",
      key: "username",
      sorter: (a, b) => a.username?.localeCompare(b.username) || 0,
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
      sorter: (a, b) => a.email?.localeCompare(b.email) || 0,
    },
    {
      title: "Phone",
      dataIndex: "mobileNumber",
      key: "mobileNumber",
      render: (phone) => phone || "N/A",
    },
    {
      title: "Status",
      dataIndex: "isActive",
      key: "isActive",
      render: (isActive) => (
        <Tag color={isActive ? "green" : "red"}>
          {isActive ? "Active" : "Inactive"}
        </Tag>
      ),
    },
    {
      title: "Profession",
      dataIndex: "profession",
      key: "profession",
      render: (profession) => profession || "N/A",
    },
  ];

  return (
    <>
      <div className="mb-8">
        <h3 className="text-3xl font-bold text-slate-800 tracking-tight">
          My Students
        </h3>
        <p className="text-slate-600 mt-2">
          View all students enrolled in your courses
        </p>
      </div>

      <Card className="shadow-xl">
        <Table
          columns={columns}
          dataSource={students}
          loading={loading}
          rowKey="id"
          pagination={{
            pageSize: 10,
            showSizeChanger: true,
            showQuickJumper: true,
            showTotal: (total) => `Total ${total} students`,
          }}
          scroll={{ x: 1200 }}
        />
      </Card>
    </>
  );
}

export default InstructorStudents;

