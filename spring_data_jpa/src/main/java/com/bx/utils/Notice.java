package com.bx.utils;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/8 9:59
 * @description 注意事项
 */
public class Notice {
    public static void main(String[] args) {
        //1、CrudRepository中的deleteById源码
        /**
         * 1. deleteById源码
         * 1.1、可以看出传入的ID不能为空
         * 1.2、调用findById方法，如果返回结果为null，则抛出异常不存在该ID的实体
         * 1.3、调用delete方法，如果传入的实体为null，则抛出异常实体不能为null
         */
//        @Transactional
//        public void deleteById(ID id) {
//            Assert.notNull(id, "The given id must not be null!");
//            this.delete(this.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(String.format("No %s entity with id %s exists!", this.entityInformation.getJavaType(), id), 1)));
//        }
//
//        @Transactional
//        public void delete(T entity) {
//            Assert.notNull(entity, "Entity must not be null!");
//            if (!this.entityInformation.isNew(entity)) {
//                Class<?> type = ProxyUtils.getUserClass(entity);
//                T existing = (T)this.em.find(type, this.entityInformation.getId(entity));
//                if (existing != null) {
//                    this.em.remove(this.em.contains(entity) ? entity : this.em.merge(entity));
//                }
//            }
//        }


        //2、CrudRepository中的findById源码
        /**
         * 2. findById源码
         * 1.1、可以看出传入的ID不能为空
         */
//        public Optional<T> findById(ID id) {
//            Assert.notNull(id, "The given id must not be null!");
//            Class<T> domainType = this.getDomainClass();
//            if (this.metadata == null) {
//                return Optional.ofNullable(this.em.find(domainType, id));
//            } else {
//                LockModeType type = this.metadata.getLockModeType();
//                Map<String, Object> hints = this.getQueryHints().withFetchGraphs(this.em).asMap();
//                return Optional.ofNullable(type == null ? this.em.find(domainType, id, hints) : this.em.find(domainType, id, type, hints));
//            }
//        }
    }
}
