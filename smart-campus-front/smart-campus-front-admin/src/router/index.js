import { createRouter, createWebHistory } from 'vue-router'

import Dashboard from '@/views/home/Dashboard.vue'
import Department from '@/views/basicData/Department.vue'
import Major from '@/views/basicData/Major.vue'
import ClassManage from '@/views/basicData/Class.vue'
import Student from '@/views/basicData/Student.vue'
import Teacher from '@/views/basicData/Teacher.vue'
import Resource from '@/views/resource/Resource.vue'
import Course from '@/views/teaching/Course.vue'
import Exercise from '@/views/teaching/Exercise.vue'
import ExamPaper from '@/views/teaching/ExamPaper.vue'
import Exam from '@/views/teaching/Exam.vue'
import Notice from '@/views/system/Notice.vue'
import Permission from '@/views/system/Permission.vue'

const routes = [
  { path: '/', redirect: '/home/dashboard' },
  {
    path: '/home/dashboard',
    component: Dashboard,
    meta: { needLogin: true, menuCode: 'dashboard' }
  },
  {
    path: '/basicData/department',
    component: Department,
    meta: { needLogin: true, menuCode: 'department' }
  },
  {
    path: '/basicData/major',
    component: Major,
    meta: { needLogin: true, menuCode: 'major' }
  },
  {
    path: '/basicData/class',
    component: ClassManage,
    meta: { needLogin: true, menuCode: 'class' }
  },
  {
    path: '/basicData/student',
    component: Student,
    meta: { needLogin: true, menuCode: 'student' }
  },
  {
    path: '/basicData/teacher',
    component: Teacher,
    meta: { needLogin: true, menuCode: 'teacher' }
  },
  {
    path: '/resource/resource',
    component: Resource,
    meta: { needLogin: true, menuCode: 'resource' }
  },
  {
    path: '/teaching/course',
    component: Course,
    meta: { needLogin: true, menuCode: 'course' }
  },
  {
    path: '/teaching/exercise',
    component: Exercise,
    meta: { needLogin: true, menuCode: 'exercise' }
  },
  {
    path: '/teaching/examPaper',
    component: ExamPaper,
    meta: { needLogin: true, menuCode: 'examPaper' }
  },
  {
    path: '/teaching/exam',
    component: Exam,
    meta: { needLogin: true, menuCode: 'exam' }
  },
  {
    path: '/system/notice',
    component: Notice,
    meta: { needLogin: true, menuCode: 'notice' }
  },
  {
    path: '/system/permission',
    component: Permission,
    meta: { needLogin: true, menuCode: 'permission' }
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router
