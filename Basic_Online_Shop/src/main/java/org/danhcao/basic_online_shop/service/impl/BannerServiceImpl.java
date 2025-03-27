package org.danhcao.basic_online_shop.service.impl;

import org.danhcao.basic_online_shop.entity.Banner;
import org.danhcao.basic_online_shop.exception.ErrorHandler;
import org.danhcao.basic_online_shop.generic.GenericService;
import org.danhcao.basic_online_shop.repository.BannerRepository;
import org.danhcao.basic_online_shop.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private GenericService genericService;

    @Override
    public void save(Banner banner) {
        try {
            bannerRepository.save(banner);
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving banner");
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            Banner banner = bannerRepository.findById(id)
                    .orElseThrow(() -> new ErrorHandler(HttpStatus.NOT_FOUND, "Banner not found"));
            bannerRepository.delete(banner);
        } catch (ErrorHandler e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Error while deleting banner");
        }
    }

    @Override
    public Iterator<Banner> findAll() {
        try {
            return bannerRepository.findAll().iterator();
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving banners");
        }
    }

    @Override
    public Banner findOne(Integer id) {
        return bannerRepository.findById(id)
                .orElseThrow(() -> new ErrorHandler(HttpStatus.NOT_FOUND, "Banner not found"));
    }

    public Page<Banner> getBanners(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return bannerRepository.findAll(pageable);
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving banners");
        }
    }

    public Banner createBanner(MultipartFile file, String status)  {
        try {
            String filePath = genericService.saveFile(file, "banners/");
            Banner banner = new Banner();
            banner.setImgUrl(filePath);
            banner.setStatus(status);
            return bannerRepository.save(banner);
        } catch (IOException e) {
            throw new ErrorHandler(HttpStatus.BAD_REQUEST, "Error saving file: " + e.getMessage());
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error while creating banner");
        }
    }

    public Banner updateBanner(int id, MultipartFile file, String status)  {
        try {
            Banner banner = findOne(id);
            String filePath = genericService.saveFile(file, "banners/");
            banner.setImgUrl(filePath);
            banner.setStatus(status);
            return bannerRepository.save(banner);
        } catch (ErrorHandler e) {
            throw e;
        } catch (IOException e) {
            throw new ErrorHandler(HttpStatus.BAD_REQUEST, "Error updating file: " + e.getMessage());
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error while updating banner");
        }
    }
}
