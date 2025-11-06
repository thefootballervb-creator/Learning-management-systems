import { useState, useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit, faTrash, faPlus, faBookOpen, faUsers } from "@fortawesome/free-solid-svg-icons";
import { message } from "antd";
import { instructorService } from "../../api/instructor.service";
import CourseModal from "./CourseModal";
import DeleteModal from "./DeleteModal";

function InstructorCourses() {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(false);

  const [courseModal, setCourseModal] = useState({
    isOpen: false,
    mode: "add",
    courseId: null,
  });

  const [deleteModal, setDeleteModal] = useState({
    isOpen: false,
    course: null,
  });

  const [studentsModal, setStudentsModal] = useState({
    isOpen: false,
    course: null,
    students: [],
  });

  useEffect(() => {
    fetchCourses();
  }, []);

  const fetchCourses = async () => {
    setLoading(true);
    try {
      const result = await instructorService.getMyCourses();
      if (result.success) {
        setCourses(result.data);
      } else {
        message.error(result.error);
      }
    } catch {
      message.error("Failed to fetch courses");
    } finally {
      setLoading(false);
    }
  };

  const openAddCourseModal = () => {
    setCourseModal({ isOpen: true, mode: "add", courseId: null });
  };

  const openEditCourseModal = (course) => {
    setCourseModal({ isOpen: true, mode: "edit", courseId: course.course_id });
  };

  const closeCourseModal = () => {
    setCourseModal({ isOpen: false, mode: "add", courseId: null });
  };

  const handleCourseSuccess = () => {
    fetchCourses();
  };

  const openDeleteModal = (course) => {
    setDeleteModal({ isOpen: true, course });
  };

  const closeDeleteModal = () => {
    setDeleteModal({ isOpen: false, course: null });
  };

  const handleDeleteCourse = async (course) => {
    return await instructorService.deleteCourse(course.course_id);
  };

  const handleDeleteSuccess = () => {
    fetchCourses();
  };

  const handleViewStudents = async (course) => {
    try {
      const result = await instructorService.getCourseStudents(course.course_id);
      if (result.success) {
        setStudentsModal({
          isOpen: true,
          course,
          students: result.data,
        });
      } else {
        message.error(result.error);
      }
    } catch {
      message.error("Failed to fetch students");
    }
  };

  const closeStudentsModal = () => {
    setStudentsModal({ isOpen: false, course: null, students: [] });
  };

  return (
    <div className="max-w-7xl mx-auto">
      <div className="bg-white rounded-2xl shadow-lg border border-gray-100 overflow-hidden">
        <div className="px-8 py-6 border-b border-gray-100 bg-gradient-to-br from-indigo-100 to-purple-100">
          <div className="flex items-center justify-between">
            <div>
              <h1 className="text-3xl font-bold text-gray-900 mb-1">My Courses</h1>
              <p className="text-gray-600">Manage your courses and track student progress</p>
            </div>
            <button
              onClick={openAddCourseModal}
              className="bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-700 hover:to-indigo-700 text-white rounded-xl px-6 py-3 font-semibold flex items-center gap-3 transform hover:scale-105 transition-all duration-200 shadow-lg hover:shadow-xl"
            >
              <FontAwesomeIcon icon={faPlus} className="text-sm" />
              Add New Course
            </button>
          </div>
        </div>

        <div className="p-8">
          {loading ? (
            <div className="flex flex-col items-center justify-center py-20">
              <div className="animate-spin rounded-full h-12 w-12 border-4 border-blue-200 border-t-blue-600"></div>
              <p className="mt-4 text-gray-600 font-medium">Loading your courses...</p>
            </div>
          ) : courses.length === 0 ? (
            <div className="flex flex-col items-center justify-center py-20 text-center">
              <div className="w-24 h-24 bg-gray-100 rounded-full flex items-center justify-center mb-6">
                <FontAwesomeIcon icon={faBookOpen} className="text-3xl text-gray-400" />
              </div>
              <h3 className="text-xl font-semibold text-gray-800 mb-2">No courses yet</h3>
              <p className="text-gray-500 mb-8 max-w-md">
                Get started by creating your first course. You can add content, manage students, and track progress.
              </p>
              <button
                onClick={openAddCourseModal}
                className="bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-700 hover:to-indigo-700 text-white rounded-xl px-8 py-4 font-semibold transform hover:scale-105 transition-all duration-200 shadow-lg hover:shadow-xl"
              >
                Create Your First Course
              </button>
            </div>
          ) : (
            <div className="grid gap-4">
              {courses.map((course) => (
                <div key={course.course_id} className="group bg-white border border-gray-200 rounded-xl hover:shadow-lg hover:border-blue-200 transition-all duration-300 overflow-hidden">
                  <div className="p-6 flex items-start justify-between">
                    <div className="flex-1 min-w-0">
                      <div className="flex items-center gap-3 mb-3">
                        <h3 className="text-xl font-bold text-gray-900 truncate">{course.course_name}</h3>
                        <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-green-100 text-green-800">Active</span>
                      </div>
                      <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-4">
                        {course.instructor && (
                          <div className="flex items-center gap-2">
                            <div className="w-2 h-2 bg-blue-500 rounded-full"></div>
                            <span className="text-sm text-gray-600">Instructor:</span>
                            <span className="text-sm font-medium text-gray-900">{course.instructor}</span>
                          </div>
                        )}
                        {course.price && (
                          <div className="flex items-center gap-2">
                            <div className="w-2 h-2 bg-green-500 rounded-full"></div>
                            <span className="text-sm text-gray-600">Price:</span>
                            <span className="text-lg font-bold text-green-600">${course.price}</span>
                          </div>
                        )}
                        <div className="flex items-center gap-2">
                          <div className="w-2 h-2 bg-purple-500 rounded-full"></div>
                          <span className="text-sm text-gray-600">Description:</span>
                          <span className="text-sm font-medium text-gray-900 truncate">{course.description || "N/A"}</span>
                        </div>
                      </div>
                    </div>
                    {/* Actions */}
                    <div className="flex items-center gap-2 ml-6">
                      <button 
                        onClick={() => handleViewStudents(course)} 
                        className="inline-flex items-center gap-2 px-4 py-2 bg-gradient-to-r from-green-600 to-emerald-600 hover:from-green-700 hover:to-emerald-700 text-white text-sm font-medium rounded-lg transform hover:scale-105 transition-all duration-200 shadow-md hover:shadow-lg"
                      >
                        <FontAwesomeIcon icon={faUsers} className="text-sm" /> View Students
                      </button>
                      <button onClick={() => openEditCourseModal(course)} className="p-2.5 rounded-lg text-gray-500 hover:text-blue-600 hover:bg-blue-50 transition-all duration-200">
                        <FontAwesomeIcon icon={faEdit} className="w-4 h-4" />
                      </button>
                      <button onClick={() => openDeleteModal(course)} className="p-2.5 rounded-lg text-gray-500 hover:text-red-600 hover:bg-red-50 transition-all duration-200">
                        <FontAwesomeIcon icon={faTrash} className="w-4 h-4" />
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>

      {/* Modals */}
      <CourseModal
        isOpen={courseModal.isOpen}
        onClose={closeCourseModal}
        onSuccess={handleCourseSuccess}
        courseId={courseModal.courseId}
        mode={courseModal.mode}
        useInstructorService={true}
      />
      <DeleteModal
        isOpen={deleteModal.isOpen}
        onClose={closeDeleteModal}
        onSuccess={handleDeleteSuccess}
        onDelete={handleDeleteCourse}
        item={deleteModal.course}
        itemType="Course"
        title="Delete Course"
        description="Are you sure you want to delete this course?"
        itemDisplayName={deleteModal.course?.course_name}
      />

      {/* Students Modal */}
      {studentsModal.isOpen && (
        <div className="fixed inset-0 flex items-center justify-center bg-black/60 backdrop-blur-lg z-50">
          <div className="bg-white rounded-2xl shadow-2xl p-8 w-full max-w-2xl max-h-[80vh] overflow-y-auto">
            <div className="flex items-center justify-between mb-6">
              <h2 className="text-2xl font-bold text-gray-900">
                Students in {studentsModal.course?.course_name}
              </h2>
              <button
                onClick={closeStudentsModal}
                className="text-gray-400 hover:text-gray-600 text-2xl"
              >
                ×
              </button>
            </div>
            {studentsModal.students.length === 0 ? (
              <div className="text-center py-12">
                <p className="text-gray-500">No students enrolled in this course yet.</p>
              </div>
            ) : (
              <div className="space-y-3">
                {studentsModal.students.map((student) => (
                  <div key={student.id} className="flex items-center gap-4 p-4 bg-gray-50 rounded-lg">
                    <div className="w-12 h-12 bg-blue-500 rounded-full flex items-center justify-center text-white font-semibold">
                      {student.username?.charAt(0).toUpperCase() || "U"}
                    </div>
                    <div className="flex-1">
                      <h3 className="font-semibold text-gray-900">{student.username}</h3>
                      <p className="text-sm text-gray-600">{student.email}</p>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
}

export default InstructorCourses;

