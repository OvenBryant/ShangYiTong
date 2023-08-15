import request from '@/utils/request'


export default{
  // 获取医院信息列表
  getHospSetList(pageNum,pageSize,searchObj){
    return request({
        url: `/admin/hosp/hospitalSet/page/${pageNum}/${pageSize}`,
        method: 'post',
        data:searchObj  // 使用json
    })
  },
  // 根据id删除医院
  deleteById(id){
    return request({
      url: '/admin/hosp/hospitalSet/'+id,
      method:'delete'
    })
  },
  // 锁定与解锁医院
  lockHospitalSet(id,status){
    return request({
      url: `/admin/hosp/hospitalSet/lockHospitalSet/${id}/${status}`,
      method:'put'
    })
  },
  // 批量删除
  batchRemoveHospSet(idList){
    return request({
      url: '/admin/hosp/hospitalSet/deleteSelected',
      method: 'delete',
      data: idList
    })
  },
  // 添加医院信息
  addHospset(hospital){
    return request({
      url: '/admin/hosp/hospitalSet/add',
      method: 'post',
      data: hospital
    })
  },
  // 根据id修改医院信息
  updateByIdHospSet(hospital){
    return request({
      url: '/admin/hosp/hospitalSet/update',
      method: 'put',
      data: hospital
    })
  }

}
