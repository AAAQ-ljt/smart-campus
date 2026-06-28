const menuConfig = [
  {
    code: 'home',
    name: '首页',
    icon: 'HomeFilled',
    children: [
      { code: 'dashboard', name: '数据看板', path: '/home/dashboard' }
    ]
  },
  {
    code: 'basicData',
    name: '基础数据',
    icon: 'DataAnalysis',
    children: [
      { code: 'department', name: '院系管理', path: '/basicData/department' },
      { code: 'major', name: '专业管理', path: '/basicData/major' },
      { code: 'class', name: '班级管理', path: '/basicData/class' },
      { code: 'student', name: '学生管理', path: '/basicData/student' },
      { code: 'teacher', name: '教师管理', path: '/basicData/teacher' }
    ]
  },
  {
    code: 'resource',
    name: '资源中心',
    icon: 'FolderOpened',
    children: [
      { code: 'resource', name: '资源管理', path: '/resource/resource' }
    ]
  },
  {
    code: 'teaching',
    name: '教学业务',
    icon: 'Reading',
    children: [
      { code: 'course', name: '课程管理', path: '/teaching/course' },
      { code: 'exercise', name: '习题管理', path: '/teaching/exercise' },
      { code: 'examPaper', name: '试卷管理', path: '/teaching/examPaper' },
      { code: 'exam', name: '考试管理', path: '/teaching/exam' }
    ]
  },
  {
    code: 'system',
    name: '系统管理',
    icon: 'Setting',
    children: [
      { code: 'notice', name: '公告管理', path: '/system/notice' },
      { code: 'permission', name: '权限管理', path: '/system/permission' }
    ]
  }
]

function getMenuByPath(path) {
  for (const menu of menuConfig) {
    for (const child of menu.children) {
      if (child.path === path) return { primary: menu, secondary: child }
    }
  }
  return null
}

function getPrimaryMenu(code) {
  return menuConfig.find(m => m.code === code) || null
}

function getAllMenuCodes() {
  const codes = []
  for (const menu of menuConfig) {
    for (const child of menu.children) {
      codes.push(child.code)
    }
  }
  return codes
}

export { menuConfig, getMenuByPath, getPrimaryMenu, getAllMenuCodes }
