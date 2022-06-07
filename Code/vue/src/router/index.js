import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/User.vue'
import Layout from "../layout/Layout.vue";

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:"/Bms",
    children:[
      {
        path:'user',
        name:'User',
        component:() => import('../views/User')
      },
      {
        path:'news',
        name:'News',
        component:() => import('../views/News')
      },
      {
        path:'bms',
        name:'Bms',
        component:() => import('../views/Bms')
      },
      {
        path:'index',
        name:'index',
        component:() => import('../views/Index')
      },
      {
        path:'indexcomment',
        name:'IndexComment',
        component:() => import('../views/IndexComment')
      },
      {
        path:'indexstore',
        name:'IndexStore',
        component:() => import('../views/IndexStore')
      },
      {
        path:'person',
        name:'Person',
        component:() => import('../views/Person')
      },
      {
        path:'details',
        name:'Details',
        component:() => import('../views/Details')
      },
      {
        path:'bbs',
        name:'Bbs',
        component:() => import('../views/Bbs')
      },
      {
        path:'bbsstudy',
        name:'bbsStudy',
        component:() => import('../views/bbsStudy')
      },
      {
        path:'bbsfood',
        name:'bbsFood',
        component:() => import('../views/bbsFood')
      },
      {
        path:'bbsshop',
        name:'bbsShop',
        component:() => import('../views/bbsShop')
      },
      {
        path:'bbstravel',
        name:'bbsTravel',
        component:() => import('../views/bbsTravel')
      },
      {
        path:'bbsadmin',
        name:'BbsAdmin',
        component:() => import('../views/BbsAdmin')
      },
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import("@/views/Login")
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import("@/views/Register")
  },
]
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
