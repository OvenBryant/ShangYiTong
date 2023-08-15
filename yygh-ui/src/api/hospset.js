import request from '@/utils/request'


export default{
  getHospSetList(pageNum,pageSize,searchObj){
    return request({
        url: `/admin/hosp/hospitalSet/page/${pageNum}/${pageSize}`,
        method: 'post',
        data:searchObj  // 使用json
    })
  },
  deleteById(id){
    return request({
      url: '/admin/hosp/hospitalSet/'+id,
      method:'delete'
    })
  },
  lockHospitalSet(id,status){
    return request({
      url: `/admin/hosp/hospitalSet/lockHospitalSet/${id}/${status}`,
      method:'put'
    })
  }
}
