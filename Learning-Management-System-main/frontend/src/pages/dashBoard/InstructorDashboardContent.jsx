import React, { useState, useEffect } from "react";
import { instructorService } from "../../api/instructor.service";

function InstructorDashboardContent({ isAuthenticated }) {
  const [coursesCount, setCoursesCount] = useState(0);
  const [studentsCount, setStudentsCount] = useState(0);
  const [enrollmentsCount, setEnrollmentsCount] = useState(0);

  useEffect(() => {
    if (!isAuthenticated) {
      return;
    }

    async function fetchData() {
      const coursesRes = await instructorService.getMyCourses();
      if (coursesRes.success) {
        setCoursesCount(coursesRes.data.length);
        
        // Calculate total enrollments across all courses
        let totalEnrollments = 0;
        const enrollmentsPromises = coursesRes.data.map(async (course) => {
          const enrollmentsRes = await instructorService.getCourseEnrollments(course.course_id);
          if (enrollmentsRes.success) {
            return enrollmentsRes.data.length;
          }
          return 0;
        });
        
        const enrollments = await Promise.all(enrollmentsPromises);
        totalEnrollments = enrollments.reduce((sum, count) => sum + count, 0);
        setEnrollmentsCount(totalEnrollments);
      }

      const studentsRes = await instructorService.getMyStudents();
      if (studentsRes.success) {
        setStudentsCount(studentsRes.data.length);
      }
    }

    fetchData();
  }, [isAuthenticated]);

  return (
    <>
      {/* Header */}
      <div className="flex items-center justify-between flex-wrap gap-4 mb-10">
        <h1 className="text-4xl font-bold text-slate-800 tracking-tight">
          Instructor Dashboard
        </h1>
      </div>

      {/* Info Cards */}
      <ul className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
        {/* Courses */}
        <li className="group bg-white/60 backdrop-blur-xl rounded-3xl p-8 flex items-center gap-6 shadow-lg hover:shadow-2xl border border-white/30 transition-transform duration-300 hover:-translate-y-2">
          <div className="w-20 h-20 flex items-center justify-center rounded-2xl bg-gradient-to-br from-blue-500 to-indigo-600 text-white text-4xl shadow-md group-hover:scale-110 transition-transform duration-300">
            <i className="bx bxs-book" />
          </div>
          <div>
            <h3 className="text-3xl font-bold text-slate-900">{coursesCount}</h3>
            <p className="text-slate-600 text-lg">My Courses</p>
          </div>
        </li>

        {/* Students */}
        <li className="group bg-white/60 backdrop-blur-xl rounded-3xl p-8 flex items-center gap-6 shadow-lg hover:shadow-2xl border border-white/30 transition-transform duration-300 hover:-translate-y-2">
          <div className="w-20 h-20 flex items-center justify-center rounded-2xl bg-gradient-to-br from-yellow-400 to-orange-500 text-white text-4xl shadow-md group-hover:scale-110 transition-transform duration-300">
            <i className="bx bxs-group" />
          </div>
          <div>
            <h3 className="text-3xl font-bold text-slate-900">{studentsCount}</h3>
            <p className="text-slate-600 text-lg">Total Students</p>
          </div>
        </li>

        {/* Enrollments */}
        <li className="group bg-white/60 backdrop-blur-xl rounded-3xl p-8 flex items-center gap-6 shadow-lg hover:shadow-2xl border border-white/30 transition-transform duration-300 hover:-translate-y-2">
          <div className="w-20 h-20 flex items-center justify-center rounded-2xl bg-gradient-to-br from-emerald-400 to-green-600 text-white text-4xl shadow-md group-hover:scale-110 transition-transform duration-300">
            <i className="bx bxs-calendar-check" />
          </div>
          <div>
            <h3 className="text-3xl font-bold text-slate-900">{enrollmentsCount}</h3>
            <p className="text-slate-600 text-lg">Total Enrollments</p>
          </div>
        </li>
      </ul>
    </>
  );
}

export default InstructorDashboardContent;

